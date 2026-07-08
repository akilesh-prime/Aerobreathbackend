package com.example.demo.controller;

import com.example.demo.dto.MetricDto;
import com.example.demo.entity.AirQualityMetric;
import com.example.demo.service.MetricProcessorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
public class MetricController {
    private final MetricProcessorService metricProcessorService;

    @PostMapping
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'ENVIRONMENTAL_OFFICER')")
    public ResponseEntity<AirQualityMetric> recordMetric(@Valid @RequestBody MetricDto dto) {
        return ResponseEntity.ok(metricProcessorService.recordMetric(dto.getStationId(), dto));
    }
}
