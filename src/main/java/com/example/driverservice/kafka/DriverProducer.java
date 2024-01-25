package com.example.driverservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(Object message) {
        log.info("Sending message {}", message);
        kafkaTemplate.send("driver-available",message);
    }
}
