package com.example.driverservice.kafka;

import com.example.driverservice.exception.UserNotFoundException;
import com.example.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RidesConsumer {

    private final DriverService driverService;

    @KafkaListener(topics = "driver-search")
    public void receiveMessage(Integer id)  {
        log.info("Receiver request for Ride {}", id);
        driverService.findAvailableDriver(id);
    }
}
