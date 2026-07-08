package com.example.demo.service;

import com.example.demo.dto.CorrelationReport;
import com.example.demo.entity.HealthObservation;
import com.example.demo.repository.AirQualityMetricRepository;
import com.example.demo.repository.HealthObservationRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CorrelationService {
    private final AirQualityMetricRepository metricRepository;
    private final HealthObservationRepository observationRepository;

    @Transactional(readOnly = true)
    public CorrelationReport analyzeCityCorrelations(String city) {
        Double averageAqi = metricRepository.calculateAverageAqiForCity(city);
        List<HealthObservation> observations = observationRepository.findRecentByCity(city, LocalDateTime.now().minusDays(30));
        double averageSymptoms = observations.stream()
                .mapToInt(HealthObservation::getSymptomScore)
                .average()
                .orElse(0);
        String interpretation = buildInterpretation(averageAqi, averageSymptoms);
        return new CorrelationReport(city, averageAqi == null ? 0 : averageAqi, averageSymptoms, observations.size(), interpretation);
    }

    private String buildInterpretation(Double averageAqi, double averageSymptoms) {
        if (averageAqi == null) {
            return "No air quality metrics are available for this city yet.";
        }
        if (averageAqi >= 150 && averageSymptoms >= 6) {
            return "High AQI and elevated symptom scores suggest an urgent respiratory risk pattern.";
        }
        if (averageAqi >= 100 || averageSymptoms >= 5) {
            return "Moderate risk detected. Continue monitoring air quality and symptom trends.";
        }
        return "Current readings indicate a low short-term respiratory risk pattern.";
    }
}
