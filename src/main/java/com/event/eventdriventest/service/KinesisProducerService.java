package com.event.eventdriventest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KinesisProducerService {

//    final private KinesisClient kinesisClient;
//    final private PocTestMapper pocTestMapper;
//
//    public void sendMessage(String streamName) {
//        List<TpSsUserStngShctBtnDTO > list = pocTestMapper.getTpSsUserStngShctBtnList();
//
//        try {
//            String JsonData = new ObjectMapper().writeValueAsString(list.get(0).toString());
//            log.info("Sent to Kinesis: {}", JsonData);
//            PutRecordRequest request = PutRecordRequest.builder()
//                    .partitionKey("nextPos-poc-1")
//                    .streamName(streamName)
//                    .data(SdkBytes.fromByteBuffer(ByteBuffer.wrap(JsonData.getBytes())))
//                    .build();
//
//            String response = kinesisClient.putRecord(request).toString();
//            log.info("Received response: {}", response);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//    }
}

