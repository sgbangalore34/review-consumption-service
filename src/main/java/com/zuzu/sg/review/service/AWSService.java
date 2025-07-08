package com.zuzu.sg.review.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AWSService {

    @Autowired
    S3Client s3Client;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    public List<String> readFileFromS3(String fileName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        ResponseInputStream<GetObjectResponse> responseResponseInputStream = s3Client.getObject(getObjectRequest);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(responseResponseInputStream));
        return bufferedReader.lines().collect(Collectors.toList());
    }
}