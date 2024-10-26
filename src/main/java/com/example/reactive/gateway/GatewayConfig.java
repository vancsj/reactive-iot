package com.example.reactive.gateway;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * The configuration for the gateway
 * Notice that in production this shall be a service to retrieve configurations from some storage like DB
 * Here a static map is used for demonstration purpose
 *
 * @author vancsj
 * @date 2024/10/23
 */
public class GatewayConfig {
    /**
     * mapping between udp port to sensor topic
     */
    public static BiMap<String, Integer> UDP_PORT_TO_TOPIC_MAP = HashBiMap.create();

    static {
        UDP_PORT_TO_TOPIC_MAP.put("temperature", 3344);
        UDP_PORT_TO_TOPIC_MAP.put("humidity", 3355);
    }
}
