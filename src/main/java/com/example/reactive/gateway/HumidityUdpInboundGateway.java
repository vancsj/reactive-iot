package com.example.reactive.gateway;

import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.ReactiveMessageHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * the gateway to collect data from humidity sensors
 *
 * @author vancsj
 * @date 2024/10/24
 */
@Slf4j
@Component
public class HumidityUdpInboundGateway implements ReactiveMessageHandler {

    @Resource
    GeneralGatewayManager gatewayManager;

    @Override
    public @NotNull Mono<Void> handleMessage(@NotNull final Message<?> message) {
        return gatewayManager.decodeAndSendToBroker(message, "humidity");
    }
}