package com.example.reactive.protocol;

import com.example.reactive.model.SensorMessage;
import org.springframework.messaging.Message;

/**
 * the message decoder interface
 *
 * @author vancsj
 * @date 2024/10/25
 */
public interface IMessageDecoder {
    /**
     * decode spring integration message into sensor message
     *
     * @param data spring integration message
     * @return decoded sensor message
     */
    SensorMessage decode(Message<?> data);
}