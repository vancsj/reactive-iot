package com.example.reactive.model;

import lombok.Data;

import java.net.URI;

/**
 * the sensor report data model
 *
 * @author vancsj
 * @date 2024/10/24
 */
@Data
public class SensorMessage {
    /**
     * the sensor id
     */
    private String sensorId;

    /**
     * the sensor value
     * set to string in case some sensors may report multiple values at once
     */
    private String value;

    /**
     * the URI of the control port for device
     * designed for scenes that we can't directly contact the device, eg. when device is behind a firewall
     * then we can control the device through the source ip and port as the SPI protocol would allow forwarding
     */
    private URI controlUri;

    /**
     * check whether the message is valid
     *
     * @return is valid message
     */
    public boolean validate() {
        return sensorId != null && value != null;
    }
}