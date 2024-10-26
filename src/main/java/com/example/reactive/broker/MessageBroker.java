package com.example.reactive.broker;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * This class simulates a message broker like rabbitmq or kafka, employing the sinks abstraction in reactor
 * It shall not be used in production, but suitable for demonstration and testing purpose
 *
 * @author vancsj
 * @date 2024/10/25
 */
@Slf4j
@Component
public class MessageBroker implements IMessageBroker {
    private final Map<String, Sinks.Many<String>> topicFluxes = new ConcurrentHashMap<>();

    /**
     * send message to specified topic
     *
     * @param topic   the target topic
     * @param message the serialized message; use string for demonstration purpose,
     *                byte[] would be better in production to adapt to binary serialization protocols
     */
    @Override
    public Mono<Boolean> send(final String topic, final String message) {
        initTopic(topic);
        var emitResult = topicFluxes.get(topic).tryEmitNext(message);
        if (emitResult.isFailure()) {
            log.error("Failed to send message to topic {}: {}", topic, message);
        }

        return Mono.just(emitResult.isSuccess());
    }

    /**
     * initialize the topic if not exist
     *
     * @param topic target topic
     */
    private synchronized void initTopic(String topic) {
        var flux = topicFluxes.get(topic);
        if (flux == null) {
            var many = Sinks.many().multicast().<String>onBackpressureBuffer();
            topicFluxes.putIfAbsent(topic, many);
        }
    }

    /**
     * subscribe to specified topic
     *
     * @param topic                the target topic
     * @param consumer             the consumer to deal with the message
     * @param subscriptionConsumer the subscription init consumer, for backpressure purpose
     */
    @Override
    public void subscribe(final String topic, Consumer<String> consumer, Consumer<? super Subscription> subscriptionConsumer) {
        initTopic(topic);
        topicFluxes.get(topic)
                .asFlux()
                .subscribeWith(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        subscriptionConsumer.accept(s);
                    }

                    @Override
                    public void onNext(String s) {
                        consumer.accept(s);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}