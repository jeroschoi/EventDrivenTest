package com.event.eventdriventest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3ClientPoc;
    private final KinesisProducerService kinesisProducerService;

    public void uploadJson(String key, String jsonData) throws IOException {

        // resources/data/sample.json 처럼 classpath 기준 경로로 전달됨
        ClassPathResource resource = new ClassPathResource(jsonData);
        Path path = resource.getFile().toPath(); // Path 객체로 변환

        if (!Files.exists(path)) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다: " + path);
        }

        String bucketName = "gs25edge-db-large-data-poc";
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(Files.probeContentType(path))
                .build();

        s3ClientPoc.putObject(request, RequestBody.fromFile(path));
        kinesisProducerService.sendMessageWithKey(key);

    }
}
