package com.event.eventdriventest.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.awssdk.services.kinesis.KinesisClient;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class KinesisConfig {
//    private final AwsCredentialsProvider awsCredentialsProvider;
//
//    @Bean
//    public KinesisClient kinesisClient() {
//        log.info("KinesisClient bean Created");
//        return KinesisClient.builder()
//                .region(Region.of("ap-northeast-2"))
//                .credentialsProvider(awsCredentialsProvider)
//                .build();
//    }
//
//    @Bean
//    public KinesisAsyncClient kinesisAsyncClient() {
//        log.info("kinesisAsyncClient bean Created");
//        return KinesisAsyncClient.builder()
//                .region(Region.AP_NORTHEAST_2) // 원하는 리전
//                .credentialsProvider(awsCredentialsProvider) // 로컬 개발용
//                .build();
//    }
}
