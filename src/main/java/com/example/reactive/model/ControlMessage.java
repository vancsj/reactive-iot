package com.example.reactive.model;

import lombok.Data;

/**
 * the control message sent to devices
 * used to control the devices, for example, to set the report interval
 *
 * @author vancsj
 * @date 2024/10/23
 */
@Data
public class ControlMessage {
    /**
     * command
     */
    private String command;

    /**
     * command payload
     */
    private String payload;

    public static ControlMessage create(String command, String payload) {
        ControlMessage message = new ControlMessage();
        message.command = command;
        message.payload = payload;
        return message;
    }
}
