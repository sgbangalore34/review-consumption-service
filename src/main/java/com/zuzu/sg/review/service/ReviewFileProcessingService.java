package com.zuzu.sg.review.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuzu.sg.review.repository.*;
import com.zuzu.sg.review.utility.JsonConverterUtility;
import com.zuzu.sg.review.validation.ReviewJsonFieldValidator;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ReviewFileProcessingService {
    private static final Logger log = LoggerFactory.getLogger(ReviewFileProcessingService.class);

    @Value("${aws.s3.bucketName}")
    private String sourceBucketName;

    @Value("${aws.s3.archive-bucket-name}")
    private String archiveBucketName;

    @Autowired
    AWSService AWSService;
    @Autowired
    JsonConverterUtility jsonConverterUtility;
    @Autowired
    ReviewJsonFieldValidator reviewJsonFieldValidator;
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    RoomTypeRepository roomTypeRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    GradeRepository gradeRepository;
    @Autowired
    ProviderGradeRatingRepository providerGradeRatingRepository;
    @Autowired
    ProviderRepository providerRepository;
    @Autowired
    ProviderSummaryRepository providerSummaryRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    S3Client s3Client;
    /**
     * Processes the split files in the reducer bucket
     * @param sqsJsonMessage
     */
    @Transactional
    public void processSplitFile(String sqsJsonMessage) {
        String fileContent = null;
        String line;
        List<String> reviewJsons = null;
        String fileName = getUploadedReviewFileName(sqsJsonMessage);
        AtomicReference<List<String>> validationErrors = new AtomicReference<>(new ArrayList<>());
        reviewJsons = AWSService.readFileFromS3(fileName);

        reviewJsons.forEach(reviewJson -> {
            JsonConverterUtility.ConvertedEntities allEntities = null;
            try {
                allEntities = jsonConverterUtility.convertJsonToEntities(reviewJson);
            } catch (IOException e) {
                log.error("Error parsing review json: {}\n{}", reviewJson, e.getMessage());
            }
            if(null == allEntities.hotel.getHotelId() || null == allEntities.hotel.getHotelName()  || allEntities.hotel.getHotelName().isEmpty()) {
                log.error("Hotel Id or nMe missing in the review record. Review Id:{}", reviewJson);
            } else {
                validationErrors.set(reviewJsonFieldValidator.validateEntities(allEntities));
                if(validationErrors.get().isEmpty()) {
                    hotelRepository.save(allEntities.hotel);
                    countryRepository.save(allEntities.country);
                    roomTypeRepository.save(allEntities.roomType);
                    groupRepository.save(allEntities.group);
                    gradeRepository.save(allEntities.grade);
                    providerRepository.save(allEntities.provider);
                    providerGradeRatingRepository.save(allEntities.provideGradeRating);
                    providerSummaryRepository.save(allEntities.providerSummary);
                    reviewRepository.save(allEntities.review);
                }
            }
        });
        moveSourceFileToArchiveBucket(fileName);
    }
    /**
     * Once the splitting is complete, the file is moved to archive bucket for auditing
     * Copy and delete is followed
     * @param inputFile
     */
    private void moveSourceFileToArchiveBucket(String inputFile) {
        s3Client.copyObject(request -> request.sourceBucket(sourceBucketName).sourceKey(inputFile).destinationBucket(archiveBucketName).destinationKey(inputFile + "_" + LocalDateTime.now()));
        s3Client.deleteObject(request -> request.bucket(sourceBucketName).key(inputFile));
        log.info("The file {} is processed and moved to archive bucket", inputFile);
    }
    /**
     * Parsing the sqsmessage from SQS, actual filename is retrieved
     * @param sqsJsonMessage
     * @return
     */
    private String getUploadedReviewFileName(String sqsJsonMessage) {
        try {
            JsonNode rootNode = objectMapper.readTree(sqsJsonMessage);
            JsonNode recordsNode = rootNode.path("Records");
            if (recordsNode.isArray()) {
                for (JsonNode record : recordsNode) {
                    String objectKey = record.path("s3").path("object").path("key").asText();
                    if (!objectKey.isEmpty()) {
                        return objectKey;
                    } else {
                        log.error("Could not extract object key from record: " + record);
                    }
                }
            } else {
                log.error("SQS message did not contain a 'Records' array or was empty.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to process S3 event message", e);
        }
        return "";
    }
}