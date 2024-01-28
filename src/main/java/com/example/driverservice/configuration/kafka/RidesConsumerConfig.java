//package com.example.driverservice.configuration.kafka;
//
//import com.example.driverservice.dto.request.DriverRequest;
//import com.example.driverservice.dto.request.RideRequest;
//import lombok.RequiredArgsConstructor;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//
//import java.util.Map;
//
//@Configuration
//@RequiredArgsConstructor
//public class RidesConsumerConfig {
//
//    @Value("${spring.kafka.consumer.bootstrap-servers}")
//    private String servers;
//
//    @Bean
//    public ConsumerFactory<String, Object> consumerFactory() {
//        Map<String, Object> props = Map.of(
//                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers,
//                ConsumerConfig.GROUP_ID_CONFIG,"driverGroup",
//                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
//                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
//                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
//                JsonDeserializer.VALUE_DEFAULT_TYPE, DriverRequest.class,
//                JsonDeserializer.TYPE_MAPPINGS, "driverRequest:" + DriverRequest.class
//        );
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//}
