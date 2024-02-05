package com.example.driverservice.configuration.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@Configuration
@RequiredArgsConstructor
public class DriverProducerConfig {

    @Value("${topic.name.driver}")
    private String driver;
    @Value("${kafka.partitions.count}")
    private int PARTITIONS_COUNT;
    @Value("${kafka.replicas.count}")
    private int REPLICAS_COUNT;

    @Bean
    public NewTopic DriverTopic() {
        return TopicBuilder.name(driver)
                .partitions(PARTITIONS_COUNT)
                .replicas(REPLICAS_COUNT)
                .build();
    }
}
