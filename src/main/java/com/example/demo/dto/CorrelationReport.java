package com.example.demo.dto;

public record CorrelationReport(
        String city,
        Double averageAqi,
        Double averageSymptomScore,
        Integer observationCount,
        String interpretation
) {
}
