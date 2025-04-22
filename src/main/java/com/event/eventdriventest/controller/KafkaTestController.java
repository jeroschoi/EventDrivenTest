package com.event.eventdriventest.controller;


import com.event.eventdriventest.dto.TpSsUserStngShctBtnDTO;
import com.event.eventdriventest.service.KafkaProducerService;
import com.event.eventdriventest.service.KinesisProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KafkaTestController {

    private final KafkaProducerService kafkaProducerService;
    private final KinesisProducerService kinesisProducerService;

    @RequestMapping("/kafka/test")
    public void testKafka(@RequestParam Integer seqNo) {
        log.info("Kafka test method called.");
        kafkaProducerService.sendMessage("nextPos-poc",TpSsUserStngShctBtnDTO.builder()
                        .regUserId("test")
                        .lastModDttm(LocalDateTime.now())
                        .regDttm(LocalDateTime.now())
                        .lastModUserId("Tester")
                        .build().toString());
    }

    @RequestMapping("/kinesis/test")
    public void testKinesis(@RequestParam Integer seqNo) {
        log.info("testKinesis test method called.");
//        kinesisProducerService.sendMessage("nextPos-poc");
    }
}
