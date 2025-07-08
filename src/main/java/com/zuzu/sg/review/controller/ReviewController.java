package com.zuzu.sg.review.controller;

import com.zuzu.sg.review.listener.S3ReviewUploadSQSListener;
import com.zuzu.sg.review.repository.CountryRepository;
import com.zuzu.sg.review.repository.GroupRepository;
import com.zuzu.sg.review.repository.HotelRepository;
import com.zuzu.sg.review.repository.RoomTypeRepository;
import com.zuzu.sg.review.service.AWSService;
import com.zuzu.sg.review.utility.JsonConverterUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Not really used. This controller can be ignored
 */
@RestController
@RequestMapping("/s3")
public class ReviewController {
    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    AWSService AWSService;

    @Autowired
    JsonConverterUtility jsonConverterUtility;

    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    RoomTypeRepository roomTypeRepository;
    @Autowired
    GroupRepository groupRepository;

    /**
     * REST endpoint to read a file from S3.
     *
     * @param fileName The name of the file to read.
     * @return ResponseEntity with the file content or an error message.
     */
    @GetMapping("/read/{fileName}")
    public ResponseEntity<List<String>> readFile(@PathVariable String fileName) {
        try {
            List<String> fileContent = AWSService.readFileFromS3(fileName);
            fileContent.forEach(reviewJson -> {
                JsonConverterUtility.ConvertedEntities allEntities = null;
                try {
                    allEntities = jsonConverterUtility.convertJsonToEntities(reviewJson);
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
                hotelRepository.save(allEntities.hotel);
                countryRepository.save(allEntities.country);
                roomTypeRepository.save(allEntities.roomType);
                groupRepository.save(allEntities.group);

            });
            return ResponseEntity.ok(fileContent);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}