package com.example.reactive.gateway;

import com.example.reactive.model.ControlMessage;
import org.springframework.integration.ip.udp.UnicastSendingMessageHandler;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * the udp outbound gateway interface to send control message to specified device
 *
 * @author vancsj
 * @date 2024/10/23
 */
@Component
public class UdpOutboundGateway implements IOutboundGateway {

    /**
     * send control message to specified URI
     *
     * @param uri     the uri of device control port
     * @param message the control message
     * @return whether successfully sent
     */
    public Mono<Boolean> sendMessage(URI uri, ControlMessage message) {
        return Mono.fromSupplier(() -> {
            var ip = uri.getHost();
            var port = uri.getPort();
            final UnicastSendingMessageHandler handler = new UnicastSendingMessageHandler(ip, port);
            handler.handleMessage(MessageBuilder.withPayload(message).build());
            return true;
        });
    }
}
