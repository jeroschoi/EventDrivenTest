package com.event.eventdriventest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3ClientPoc;
    private final KinesisProducerService kinesisProducerService;

    public void uploadJson(String key, String jsonData) {
        String bucketName = "nextpos-poc";
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType("application/json")
                .build();

        s3ClientPoc.putObject(request, RequestBody.fromString(jsonData));
        kinesisProducerService.sendMessageWithKey(key);

    }
}
