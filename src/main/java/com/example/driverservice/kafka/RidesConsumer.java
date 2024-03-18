package com.example.driverservice.kafka;

import com.example.driverservice.dto.request.DriverRequest;
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

    @KafkaListener(topics = "${topic.name.ride}")
    public void receiveMessage(DriverRequest request)  {
        log.info("Receiver request for Ride {}", request.getId());
        driverService.findAvailableDriver(request);
    }
}
