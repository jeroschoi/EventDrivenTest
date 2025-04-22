package com.event.eventdriventest.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@Slf4j
public class KinesisStreamConsumerService {


    @Bean
    public Consumer<String> kinesisListener() {
        return message -> {
            log.info("Received from Kinesis: {}", message);
        };
    }
}
