package com.example.reactive.gateway;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.ip.dsl.Udp;

/**
 * configurations for udp protocol gateways
 *
 * @author vancsj
 * @date 2024/10/23
 */
@Configuration
public class UdpInboundConfiguration {

    @Resource
    private TemperatureUdpInboundGateway temperatureUdpInboundGateway;

    @Resource
    private HumidityUdpInboundGateway humidityUdpInboundGateway;

    /**
     * the temperature channel setup
     */
    @Bean
    public IntegrationFlow temperatureUdpIn() {
        return IntegrationFlow.from(Udp.inboundAdapter(
                        GatewayConfig.UDP_PORT_TO_TOPIC_MAP.get("temperature")
                ))
                .channel("temperature")
                .handleReactive(temperatureUdpInboundGateway);
    }

    /**
     * the humidity channel setup
     */
    @Bean
    public IntegrationFlow humidityUdpIn() {
        return IntegrationFlow.from(Udp.inboundAdapter(
                        GatewayConfig.UDP_PORT_TO_TOPIC_MAP.get("humidity")
                ))
                .channel("humidity")
                .handleReactive(humidityUdpInboundGateway);
    }
}