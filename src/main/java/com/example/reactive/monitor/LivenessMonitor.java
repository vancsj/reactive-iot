package com.example.reactive.monitor;

import com.example.reactive.broker.IMessageBroker;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * the device liveness monitor
 * designed to alarm when a device is offline
 *
 * @author vancsj
 * @date 2024/10/23
 */
@Slf4j
@Component
public class LivenessMonitor {
    @Resource
    IMessageBroker messageBroker;

    @PostConstruct
    public void init() {
        // subscribe with both temperature and humidity topic
        // here we treat the sensor report message as a heartbeat
        // there should also be a cron job to check last time a device was seen
        // once the device does not heartbeat after a threshold, alarm
        messageBroker.subscribe("temperature", (message) -> {
            log.info("received temperature heartbeat message: {}", message);
        }, subscription -> subscription.request(10));
        messageBroker.subscribe("humidity", (message) -> {
            log.info("received humidity heartbeat message: {}", message);
        }, subscription -> subscription.request(10));
    }
}
