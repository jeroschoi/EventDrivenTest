package com.event.eventdriventest.controller;


import com.event.eventdriventest.dto.TpSsUserStngShctBtnDTO;
import com.event.eventdriventest.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KafkaTestController {

    private final KafkaProducerService kafkaProducerService;

    @RequestMapping("/kafka/test")
    public void testKafka(@RequestParam Integer seqNo) {
        log.info("Kafka test method called.");
        kafkaProducerService.sendMessage("nextPos-poc",TpSsUserStngShctBtnDTO.builder()
                .btnSeqNo(seqNo)
                .otherRefVal("test")
                .btnNm("test")
                .posNo("2")
                .regUserId("test")
                .shctDistnVal("test")
                .userStngSpCd("test")
                .cvsSiteCd("test")
                .build().toString());
    }
}
