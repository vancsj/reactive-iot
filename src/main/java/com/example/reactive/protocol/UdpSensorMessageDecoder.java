package com.example.reactive.protocol;

import com.example.reactive.model.SensorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * the message decoder for general sensors sending message through udp
 *
 * @author vancsj
 * @date 2024/10/23
 */
@Slf4j
@Component
public class UdpSensorMessageDecoder implements IMessageDecoder {

    /**
     * sensor id field key
     */
    public static final String SENSOR_ID = "sensor_id";

    /**
     * value field key
     */
    public static final String VALUE = "value";

    /**
     * part count for format like value=50
     */
    public static final int KEY_VALUE_PART_COUNT = 2;

    /**
     * decode spring integration message into sensor message
     * supported message format: sensor_id=h1; value=50.1
     *
     * @param message spring integration message
     * @return decoded sensor message
     */
    @Override
    public SensorMessage decode(Message<?> message) {
        var data = message.getPayload();
        var msg = new String((byte[]) data, StandardCharsets.UTF_8);
        var sensorMessage = new SensorMessage();
        for (String part : msg.split(";")) {
            var keyValue = part.trim().split("=");
            if (keyValue.length != KEY_VALUE_PART_COUNT) {
                continue;
            }
            var key = keyValue[0].trim().toLowerCase();
            var value = keyValue[1].trim().toLowerCase();
            switch (key) {
                case SENSOR_ID:
                    sensorMessage.setSensorId(value);
                    break;
                case VALUE:
                    sensorMessage.setValue(value);
                    break;
            }
        }
        var ipAddress = message.getHeaders().get("ip_address");
        if (ipAddress != null) {
            String url = "udp://%s:12345".formatted(ipAddress);
            sensorMessage.setControlUri(URI.create(url));
        }
        log.info("device message decoded: {}", sensorMessage);

        return sensorMessage;
    }
}
