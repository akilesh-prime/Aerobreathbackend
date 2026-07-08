package com.example.demo.controller;

import com.example.demo.dto.CorrelationReport;
import com.example.demo.service.CorrelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/insights")
@RequiredArgsConstructor
public class InsightController {
    private final CorrelationService correlationService;

    @GetMapping("/correlation/{city}")
    @PreAuthorize("hasRole('MEDICAL_RESEARCHER')")
    public ResponseEntity<CorrelationReport> cityCorrelation(@PathVariable String city) {
        return ResponseEntity.ok(correlationService.analyzeCityCorrelations(city));
    }
}
