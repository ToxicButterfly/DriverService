package com.example.driverservice.kafka;

import com.example.driverservice.dto.request.RideRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverProducer {

    @Value("${topic.name.driver}")
    private String driverTopic;
    private final KafkaTemplate<String, RideRequest> kafkaTemplate;

    public void sendMessage(RideRequest request) {
        log.info("Sending message {}", request);
        Message<RideRequest> message = MessageBuilder
                .withPayload(request)
                .setHeader(KafkaHeaders.TOPIC, driverTopic)
                .build();
        kafkaTemplate.send(message);
    }
}
