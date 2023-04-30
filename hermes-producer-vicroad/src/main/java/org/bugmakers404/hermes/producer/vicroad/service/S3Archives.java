package org.bugmakers404.hermes.producer.vicroad.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Archives {

    private final S3Client s3Client;

    public void saveStringAsJsonFile(String bucketName, String objectKey, String jsonString) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .contentType("application/json")
                .build();
        RequestBody requestBody = RequestBody.fromString(jsonString, StandardCharsets.UTF_8);
        PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, requestBody);
        if (putObjectResponse.eTag() == null) {
            log.error("Failed to archive data in S3!");
            throw new IllegalStateException("Failed to archive data in S3!");
        }
    }
}
