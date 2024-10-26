package com.example.reactive.repository;

import com.example.reactive.model.SensorMessage;
import reactor.core.publisher.Mono;

/**
 * the metric repository
 * acts as the raw sensor metric storage
 * supports time-series query and can be used to aggregate high-level metrics
 * and to trigger alarms based on time-window
 *
 * @author vancsj
 * @date 2024/10/24
 */
public interface IMetricRepository {

    Mono<Boolean> save(SensorMessage sensorMessage);

}