package com.example.reactive.monitor;

import com.example.reactive.broker.IMessageBroker;
import com.example.reactive.model.SensorMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * the temperature monitor
 *
 * @author vancsj
 * @date 2024/10/23
 */
@Slf4j
@Component
public class TemperatureMonitor {
    /**
     * temperature alarm threshold
     * should be reading from some configuration storage
     * set to constant for demonstration purpose
     */
    private static final float THRESHOLD = 35.0f;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    IMessageBroker messageBroker;

    @PostConstruct
    public void init() {
        // subscribe with the message broker to receive temperature data
        messageBroker.subscribe("temperature", this::logAndAlarm, subscription -> subscription.request(10));
    }

    private void logAndAlarm(String message) {
        log.info("received temperature message: {}", message);
        try {
            var deviceMessage = objectMapper.readValue(message, SensorMessage.class);
            if (Float.parseFloat(deviceMessage.getValue()) > THRESHOLD) {
                log.warn("temperature {} is greater than {}", deviceMessage.getValue(), THRESHOLD);
            }
        } catch (JsonProcessingException e) {
            log.error("unexpected error", e);
        }
    }
}
