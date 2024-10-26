package com.example.reactive.gateway;

import com.example.reactive.broker.IMessageBroker;
import com.example.reactive.protocol.IMessageDecoder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * the general purposed gateway functions
 *
 * @author vancsj
 * @date 2024/10/23
 */
@Slf4j
@Component
public class GeneralGatewayManager {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    IMessageBroker messageBroker;

    @Resource
    IMessageDecoder messageDecoder;

    /**
     * decode the message and directly send it to the specified topic in message broker
     *
     * @param message the message received
     * @param topic   the target topic
     * @return void
     */
    Mono<Void> decodeAndSendToBroker(Message<?> message, String topic) {
        // decode message
        var deviceMessage = messageDecoder.decode(message);
        // validate message
        // maybe also authenticate device -- to be added
        if (!deviceMessage.validate()) {
            log.error("device message for {} validation failed: {}", topic, deviceMessage);
            return Mono.empty();
        }
        // send message to message queue
        try {
            var messageBody = objectMapper.writeValueAsString(deviceMessage);
            return messageBroker.send(topic, messageBody)
                    .flatMap(success -> {
                        if (success == null || !success) {
                            // if failed to send, just log and alarm
                            // do not retry since single failures doesn't matter for sensor data
                            log.error("send device message failed: {}", deviceMessage);
                        }
                        return Mono.empty();
                    });
        } catch (JsonProcessingException e) {
            // shall create an alarm for such unexpected error
            log.error("unexpected error", e);
        }

        return Mono.empty();
    }
}