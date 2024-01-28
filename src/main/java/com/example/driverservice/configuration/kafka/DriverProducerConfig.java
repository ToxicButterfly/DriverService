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

//    @Value("${spring.kafka.consumer.bootstrap-servers}")
//    private String servers;
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

    @Bean
    public StringJsonMessageConverter stringJsonMessageConverter() {
        return new StringJsonMessageConverter();
    }

//    @Bean
//    public ProducerFactory<String, Object> producerFactory() {
//        Map<String, Object> props = Map.of(
//                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers,
//                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
//                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
//                JsonSerializer.TYPE_MAPPINGS, "rideRequest:" + RideRequest.class.getName(),
//                JsonSerializer.ADD_TYPE_INFO_HEADERS, false
//        );
//        return new DefaultKafkaProducerFactory<>(props);
//    }
//
//    @Bean
//    public KafkaTemplate<String, Object> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }

}
