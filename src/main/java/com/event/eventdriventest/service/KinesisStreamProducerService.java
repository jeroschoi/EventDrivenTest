package com.event.eventdriventest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Service
@Slf4j
public class KinesisStreamProducerService {
    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    public void send(String payload) {
        if (payload == null || payload.isEmpty()) {
            log.warn("Payload is null or empty. Message will not be enqueued.");
            return;
        }
        boolean success = messageQueue.offer(payload);
        if (!success) {
            log.error("Failed to enqueue message: {}", payload);
        }
    }

    @Bean
    public Supplier<Message<String>> kinesisOutput() {
        return () -> {
            try {
                String payload = messageQueue.poll(1, TimeUnit.SECONDS);
                if (payload != null) {
                    return MessageBuilder.withPayload(payload).build();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return null;
        };
    }
}
