package com.event.eventdriventest.service;

import com.event.eventdriventest.dto.TpSsUserStngShctBtnDTO;
import com.event.eventdriventest.mapper.PocTestMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.kinesis.model.PutRecordRequest;

import java.nio.ByteBuffer;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KinesisProducerService {

    final private KinesisClient kinesisClient;
    final private PocTestMapper pocTestMapper;

    public void sendMessage(String streamName) {
        List<TpSsUserStngShctBtnDTO> list = pocTestMapper.getTpSsUserStngShctBtnList();

        try {
            String JsonData = new ObjectMapper().writeValueAsString(list.get(0).toString());
            log.info("Sent to Kinesis: {}", JsonData);
            PutRecordRequest request = PutRecordRequest.builder()
                    .partitionKey("nextPos-poc-1")
                    .streamName(streamName)
                    .data(SdkBytes.fromByteBuffer(ByteBuffer.wrap(JsonData.getBytes())))
                    .build();

            String response = kinesisClient.putRecord(request).toString();
            log.info("Received response: {}", response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

