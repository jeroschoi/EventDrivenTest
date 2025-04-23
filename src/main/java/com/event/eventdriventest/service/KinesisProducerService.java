package com.event.eventdriventest.service;

import com.event.eventdriventest.dto.TpSsUserStngShctBtnDTO;
import com.event.eventdriventest.mapper.PocTestMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kinesis.KinesisClient;
import software.amazon.awssdk.services.kinesis.model.PutRecordRequest;

import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KinesisProducerService {

    final private KinesisClient kinesisClient;
//    final private PocTestMapper pocTestMapper;

    public void sendMessage(String streamName) {
//        List<TpSsUserStngShctBtnDTO> list = pocTestMapper.getTpSsUserStngShctBtnList();

        try {
            ClassPathResource resource = new ClassPathResource("testJsonData.json");
            Path path = resource.getFile().toPath();

            // 파일 내용을 문자열로 읽기
            String JsonData = Files.readString(path);
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

    public void sendMessageWithKey(String key) {
//        List<TpSsUserStngShctBtnDTO> list = pocTestMapper.getTpSsUserStngShctBtnList();


        try {
            log.info("Sent to Kinesis Key From S3: {}", key);
            PutRecordRequest request = PutRecordRequest.builder()
                    .partitionKey("nextPos-poc-1")
                    .streamName("nextPos-poc")
                    .data(SdkBytes.fromByteBuffer(ByteBuffer.wrap(key.getBytes())))
                    .build();

            String response = kinesisClient.putRecord(request).toString();
            log.info("Received response: {}", response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

