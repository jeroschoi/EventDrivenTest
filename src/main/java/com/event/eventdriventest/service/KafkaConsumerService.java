package com.event.eventdriventest.service;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

//    @KafkaListener(topics = "nextPos-poc", groupId = "esgTest")
//    public void consume(String message) {
//        System.out.println("Consumed message: " + message);
//    }
}
