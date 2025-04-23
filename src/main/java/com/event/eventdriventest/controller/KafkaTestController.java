package com.event.eventdriventest.controller;


import com.event.eventdriventest.dto.TpSsUserStngShctBtnDTO;
import com.event.eventdriventest.service.KafkaProducerService;
import com.event.eventdriventest.service.KinesisProducerService;
import com.event.eventdriventest.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KafkaTestController {

    private final KafkaProducerService kafkaProducerService;
    private final KinesisProducerService kinesisProducerService;
    private final S3Service s3Service;


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
        kinesisProducerService.sendMessage("nextPos-poc");
    }

    @RequestMapping("/s3/test")
    public void s3(@RequestParam Integer seqNo) throws IOException {
        log.info("s3 service test method called.");
        //kinesisProducerService.sendMessage("nextPos-poc");
        String fileName = "testJsonData.json";
        //String content = new String(Files.readAllBytes(Paths.get("/testJsonData")));


        // 문자열을 JSON 객체로 변환
        //JSONObject jsonObject = new JSONObject(content);

        s3Service.uploadJson("s3/test/poc/test/" + seqNo +"/"+fileName , fileName);
    }
}
