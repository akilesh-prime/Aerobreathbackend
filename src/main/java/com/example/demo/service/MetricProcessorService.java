package com.example.demo.service;

import com.example.demo.dto.MetricDto;
import com.example.demo.entity.AirQualityMetric;
import com.example.demo.entity.SensorStation;
import com.example.demo.exception.SensorStationNotFoundException;
import com.example.demo.repository.AirQualityMetricRepository;
import com.example.demo.repository.SensorStationRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MetricProcessorService {
    private final SensorStationRepository stationRepository;
    private final AirQualityMetricRepository metricRepository;

    @Transactional
    public AirQualityMetric recordMetric(Long stationId, MetricDto dto) {
        SensorStation station = stationRepository.findById(stationId)
                .orElseThrow(() -> new SensorStationNotFoundException(stationId));
        AirQualityMetric metric = new AirQualityMetric();
        metric.setStation(station);
        metric.setAqiValue(dto.getAqi());
        metric.setPm25(dto.getPm25());
        metric.setPm10(dto.getPm10());
        metric.setRecordedAt(LocalDateTime.now());
        station.setDataCapacity(Math.min(100, (station.getDataCapacity() == null ? 0 : station.getDataCapacity()) + 5));
        stationRepository.save(station);
        return metricRepository.save(metric);
    }
}
