package com.example.reactive.gateway;

import com.example.reactive.model.ControlMessage;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * the outbound gateway interface to send control message to specified device
 *
 * @author vancsj
 * @date 2024/10/23
 */
public interface IOutboundGateway {
    /**
     * send control message to specified URI
     *
     * @param uri     the uri of device control port
     * @param message the control message
     * @return whether successfully sent
     */
    Mono<Boolean> sendMessage(URI uri, ControlMessage message);
}
