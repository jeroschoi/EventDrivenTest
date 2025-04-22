package com.event.eventdriventest.service;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.kinesis.KinesisAsyncClient;
import software.amazon.awssdk.services.kinesis.model.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KinesisConsumerService {


    private final KinesisAsyncClient kinesisAsyncClient;
    private final String streamName = "nextPos-poc";

    @PostConstruct
    public void startConsuming() {
        log.info("Starting Kinesis consumer...");
        consumeAllShards();
    }

    private void consumeAllShards() {
        kinesisAsyncClient.listShards(ListShardsRequest.builder()
                        .streamName(streamName)
                        .build())
                .thenApply(ListShardsResponse::shards)
                .thenAccept(this::processShards)
                .exceptionally(e -> {
                    log.error("Error listing shards: ", e);
                    return null;
                });
    }

    private void processShards(List<Shard> shards) {
        for (Shard shard : shards) {
            String shardId = shard.shardId();
            log.info("Processing shard: {}", shardId);

            kinesisAsyncClient.getShardIterator(GetShardIteratorRequest.builder()
                            .streamName(streamName)
                            .shardId(shardId)
                            .shardIteratorType(ShardIteratorType.TRIM_HORIZON)
                            .build())
                    .thenApply(GetShardIteratorResponse::shardIterator)
                    .thenAccept(this::consumeRecords)
                    .exceptionally(e -> {
                        log.error("Error getting shard iterator for shard {}: ", shardId, e);
                        return null;
                    });
        }
    }

    private void consumeRecords(String shardIterator) {
        kinesisAsyncClient.getRecords(GetRecordsRequest.builder()
                        .shardIterator(shardIterator)
                        .limit(25)
                        .build())
                .thenAccept(recordsResponse -> {
                    recordsResponse.records().forEach(record -> {
                        String data = StandardCharsets.UTF_8.decode(record.data().asByteBuffer()).toString();
                        log.info("Received record: {}", data);
                        // 비즈니스 로직 실행
                    });

                    String nextShardIterator = recordsResponse.nextShardIterator();
                    if (nextShardIterator != null) {
                        consumeRecords(nextShardIterator); // 재귀 호출로 다음 데이터 처리
                    }
                })
                .exceptionally(e -> {
                    log.error("Error consuming records: ", e);
                    return null;
                });
    }
}
