package com.example.reactive.protocol;

import com.example.reactive.model.SensorMessage;

/**
 * the message encoder interface
 *
 * @author vancsj
 * @date 2024/10/25
 */
public interface IMessageEncoder {
    /**
     * encode sensor message to device specific payload
     *
     * @param message the sensor message
     * @return encoded sensor message
     */
    Object encode(SensorMessage message);
}