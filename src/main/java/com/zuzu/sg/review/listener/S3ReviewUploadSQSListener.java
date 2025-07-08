package com.zuzu.sg.review.listener;

import com.zuzu.sg.review.service.ReviewFileProcessingService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class S3ReviewUploadSQSListener {
    private static final Logger log = LoggerFactory.getLogger(S3ReviewUploadSQSListener.class);

    @Autowired
    ReviewFileProcessingService reviewFileProcessingService;

    @SqsListener("review-split-file-process-sqs")
    public void receiveMessage(String sqsJsonMessage) {
        log.info("SQS event received from review-split-file-process-sqs. Event json: " + sqsJsonMessage);
        reviewFileProcessingService.processSplitFile(sqsJsonMessage);
    }

}

