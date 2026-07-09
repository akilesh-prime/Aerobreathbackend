package com.example.demo.config;

import com.example.demo.entity.AirQualityMetric;
import com.example.demo.entity.Role;
import com.example.demo.entity.SensorStation;
import com.example.demo.entity.StationStatus;
import com.example.demo.entity.SystemUser;
import com.example.demo.repository.AirQualityMetricRepository;
import com.example.demo.repository.SensorStationRepository;
import com.example.demo.repository.SystemUserRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final SystemUserRepository userRepository;
    private final SensorStationRepository stationRepository;
    private final AirQualityMetricRepository metricRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            userRepository.save(new SystemUser("officer1", "officer1@aerobreath.com", passwordEncoder.encode("password"), "Chennai", Role.ENVIRONMENTAL_OFFICER));
            userRepository.save(new SystemUser("citizen1", "citizen1@aerobreath.com", passwordEncoder.encode("password"), "Chennai", Role.CITIZEN));
            userRepository.save(new SystemUser("researcher1", "researcher1@aerobreath.com", passwordEncoder.encode("password"), "Chennai", Role.MEDICAL_RESEARCHER));
            userRepository.save(new SystemUser("admin1", "admin1@aerobreath.com", passwordEncoder.encode("password"), "Chennai", Role.SYSTEM_ADMIN));
        }

        if (stationRepository.count() == 0) {
            SensorStation station = new SensorStation();
            station.setStationName("Chennai Central Monitor");
            station.setCity("Chennai");
            station.setRegistrationId("AB-CHN-001");
            station.setLatitude(new BigDecimal("13.0827"));
            station.setLongitude(new BigDecimal("80.2707"));
            station.setStatus(StationStatus.ACTIVE);
            station.setDataCapacity(45);
            station.setAqi(118);
            station.setPm25(new BigDecimal("42.5"));
            station.setPm10(new BigDecimal("86.1"));
            SensorStation saved = stationRepository.save(station);

            AirQualityMetric metric = new AirQualityMetric();
            metric.setStation(saved);
            metric.setAqiValue(118);
            metric.setPm25(new BigDecimal("42.5"));
            metric.setPm10(new BigDecimal("86.1"));
            metric.setRecordedAt(LocalDateTime.now().minusHours(2));
            metricRepository.save(metric);
        }
    }
}
