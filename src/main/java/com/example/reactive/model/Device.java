package com.example.reactive.model;

import lombok.Data;

import java.net.URI;
import java.util.Date;

/**
 * the device info
 *
 * @author vancsj
 * @date 2024/10/23
 */
@Data
public class Device {
    /**
     * device id, for example t1 for temperature sensor 1
     */
    private String id;

    /**
     * last time seen active
     */
    private Date lastSeen;

    /**
     * the URI of the control port
     * for example: udp://127.0.0.1:12345
     */
    private URI controlUri;
}
