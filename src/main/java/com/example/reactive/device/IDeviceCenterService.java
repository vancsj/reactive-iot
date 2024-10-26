package com.example.reactive.device;

import com.example.reactive.model.Device;
import reactor.core.publisher.Mono;

/**
 * the device center
 * this class manages the device register information and sends control command to the device
 * this class can be split into separate classes if needed
 *
 * @author vancsj
 * @date 2024/10/23
 */
public interface IDeviceCenterService {
    /**
     * get device info
     *
     * @param deviceId device id
     * @return device information
     */
    Mono<Device> getDevice(String deviceId);

    /**
     * send control command to device
     *
     * @param deviceId device id
     * @param command  command to be sent
     * @param payload  command payload
     * @return whether command is successfully sent
     */
    Mono<Boolean> sendCommand(String deviceId, String command, String payload);
}
