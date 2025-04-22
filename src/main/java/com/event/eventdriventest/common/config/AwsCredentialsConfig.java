package com.event.eventdriventest.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import software.amazon.awssdk.auth.credentials.*;

@Configuration
@Slf4j
public class AwsCredentialsConfig {

    // private final String sessionToken = "";
    private final String secretKey = "";
    private final String accessKey = "";

    @Bean
    @Primary
    public AwsCredentialsProvider awsCredentialsProvider() {
        try {
            log.info("AwsCredentialsProvider Create start!!");
            AwsCredentialsProvider provider = DefaultCredentialsProvider.create();
            provider.resolveCredentials();
            return provider;
        } catch (RuntimeException e) {
            log.info("StaticCredentialsProvider Create start!!");
            AwsCredentialsProvider provider = StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(
                            accessKey,
                            secretKey
                    ));
            try {
                provider.resolveCredentials();
            } catch (RuntimeException ex) {
                throw new RuntimeException(ex);
            }
            return provider;
        }
    }
}
