package com.example.reactive.broker;

import org.reactivestreams.Subscription;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

/**
 * The interface of a message broker
 *
 * @author vancsj
 * @date 2024/10/24
 */
public interface IMessageBroker {
    /**
     * send message to specified topic
     *
     * @param topic   the target topic
     * @param message the serialized message; use string for demonstration purpose,
     *                byte[] would be better in production to adapt to binary serialization protocols
     */
    Mono<Boolean> send(String topic, String message);

    /**
     * subscribe to specified topic
     *
     * @param topic                the target topic
     * @param consumer             the consumer to deal with the message
     * @param subscriptionConsumer the subscription init consumer, for backpressure purpose
     */
    void subscribe(String topic, Consumer<String> consumer, Consumer<? super Subscription> subscriptionConsumer);
}