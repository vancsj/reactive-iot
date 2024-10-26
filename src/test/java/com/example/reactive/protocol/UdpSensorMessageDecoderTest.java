package com.example.reactive.protocol;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.nio.charset.StandardCharsets;

class UdpSensorMessageDecoderTest {
    UdpSensorMessageDecoder target = new UdpSensorMessageDecoder();

    @BeforeEach
    void setUp() {
    }

    @Test
    void decode() {
        testDecodeInner("", "127.0.0.1", false, null, null);
        testDecodeInner("abc", "127.0.0.1", false, null, null);
        testDecodeInner("abc=1", "127.0.0.1", false, null, null);
        testDecodeInner("sensor_id=h1", "127.0.0.1", false, "h1", null);
        testDecodeInner("value=40", "127.0.0.1", false, null, "40");
        testDecodeInner("sensor_id=h1;value=40", null, true, "h1", "40");
        testDecodeInner("sensor_id=h1;value=40", "127.0.0.1", true, "h1", "40");
        testDecodeInner("sensor_id=h1; value=40", "127.0.0.1", true, "h1", "40");
        testDecodeInner("sensor_id=h1 ; value=40", "127.0.0.1", true, "h1", "40");
        testDecodeInner(" sensor_id= h1 ; value= 40 ", "127.0.0.1", true, "h1", "40");
    }

    private void testDecodeInner(String payload, String ip, boolean valid, String sensorId, String value) {
        //noinspection unchecked
        Message<byte[]> message = Mockito.mock(Message.class);
        Mockito.when(message.getPayload()).thenReturn(payload.getBytes(StandardCharsets.UTF_8));
        var messageHeaders = Mockito.mock(MessageHeaders.class);
        Mockito.when(messageHeaders.get("ip_address")).thenReturn(ip);
        Mockito.when(message.getHeaders()).thenReturn(messageHeaders);
        var decoded = target.decode(message);
        Assertions.assertEquals(valid, decoded.validate());
        Assertions.assertEquals(sensorId, decoded.getSensorId());
        Assertions.assertEquals(value, decoded.getValue());
    }
}