package com.event.eventdriventest.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Configuration
@RequiredArgsConstructor
public class S3config {

    private final AwsCredentialsProvider awsCredentialsProvider;
    @Bean(name = "s3ClientPoc")
    public S3Client s3ClientPoc() {
        return S3Client.builder()
                .region(Region.AP_NORTHEAST_2) // 서울 리전 예시
                .credentialsProvider(awsCredentialsProvider)
                .build();
    }
}
