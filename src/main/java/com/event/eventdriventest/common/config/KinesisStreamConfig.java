package com.event.eventdriventest.common.config;

import io.awspring.cloud.core.region.StaticRegionProvider;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.kinesis.properties.KinesisBindingProperties;
import org.springframework.cloud.stream.binder.kinesis.properties.KinesisConsumerProperties;
import org.springframework.cloud.stream.binder.kinesis.properties.KinesisExtendedBindingProperties;
import org.springframework.cloud.stream.binder.kinesis.properties.KinesisProducerProperties;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.aws.inbound.kinesis.CheckpointMode;
import org.springframework.integration.aws.inbound.kinesis.ListenerMode;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.kinesis.model.ShardIteratorType;

import java.util.Map;

@Configuration
@EnableConfigurationProperties
@RequiredArgsConstructor
@Slf4j
public class KinesisStreamConfig {

    final private AwsCredentialsProvider awsCredentialsProvider;

    @Bean
    @Primary
    public BindingServiceProperties bindingServiceProperties() {
        BindingServiceProperties props = new BindingServiceProperties();

        BindingProperties binding = new BindingProperties();
        binding.setDestination("nextPos-poc");
        binding.setContentType("application/json");
        binding.setGroup("nextPos");

        ConsumerProperties consumerProperties = new ConsumerProperties();
        binding.setConsumer(consumerProperties);
        props.setBindings(
                Map.of("kinesisListener-in-0", binding)
        );
        return props;
    }
    @Bean
    @Primary
    public DynamoDbAsyncClient dynamoDbAsyncClient() {
        log.info("Creating DynamoDbAsyncClient");
        return DynamoDbAsyncClient.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(awsCredentialsProvider)
                .build();
    }

    @Bean(name = "customKinesisExtendedBindingProperties")
    @Primary
    public KinesisExtendedBindingProperties extendedBindingProperties() {
        KinesisExtendedBindingProperties props = new KinesisExtendedBindingProperties();

        // consumer
        KinesisConsumerProperties consumerProperties = new KinesisConsumerProperties();
        consumerProperties.setShardIteratorType(ShardIteratorType.TRIM_HORIZON.toString()); // or LATEST
        consumerProperties.setCheckpointMode(CheckpointMode.record);
        consumerProperties.setListenerMode(ListenerMode.record);

        KinesisBindingProperties kinesisConsumerBinding = new KinesisBindingProperties();
        kinesisConsumerBinding.setConsumer(consumerProperties);


        // producer
        KinesisProducerProperties producerProperties = new KinesisProducerProperties();
        producerProperties.setSync(true);

        KinesisBindingProperties kinesisProducerBinding = new KinesisBindingProperties();
        kinesisProducerBinding.setProducer(producerProperties);

        props.setBindings(
            Map.of("kinesisListener-in-0", kinesisConsumerBinding
            ,"kinesisListener-out-0", kinesisProducerBinding
                    )
        );

        return props;
    }

    @Bean
    @Primary
    public StaticRegionProvider staticRegionProvider() {
        return new StaticRegionProvider("ap-northeast-2");
    }
//
    @Bean
    public ObservationRegistry observationRegistry() {
        return ObservationRegistry.create();
    }
}
