package com.example.reactive.device;

import com.example.reactive.gateway.IOutboundGateway;
import com.example.reactive.model.ControlMessage;
import com.example.reactive.model.Device;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * the device center
 * this class manages the device register information and sends control command to the device
 * this class can be split into separate classes if needed
 *
 * @author vancsj
 * @date 2024/10/23
 */
@Component
public class DeviceCenterService implements IDeviceCenterService {
    @Resource
    IOutboundGateway outboundGateway;

    /**
     * get device info from the device register center(a kv storage)
     *
     * @param deviceId device id
     * @return device information
     */
    @Override
    public Mono<Device> getDevice(String deviceId) {
        return null;
    }

    /**
     * send control command to device
     *
     * @param deviceId device id
     * @param command  command to be sent
     * @param payload  command payload
     * @return whether command is successfully sent
     */
    @Override
    public Mono<Boolean> sendCommand(String deviceId, String command, String payload) {
        return getDevice(deviceId)
                .flatMap(device -> outboundGateway.sendMessage(
                        device.getControlUri(),
                        ControlMessage.create(command, payload)
                ));
    }
}
