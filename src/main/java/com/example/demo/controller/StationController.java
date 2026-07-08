package com.example.demo.controller;

import com.example.demo.entity.SensorStation;
import com.example.demo.service.StationService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class StationController {
    private final StationService stationService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ENVIRONMENTAL_OFFICER', 'CITIZEN', 'SYSTEM_ADMIN', 'MEDICAL_RESEARCHER')")
    public ResponseEntity<List<SensorStation>> getAllStations() {
        return ResponseEntity.ok(stationService.getAllStations());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'ENVIRONMENTAL_OFFICER')")
    public ResponseEntity<Object> createStation(@Valid @RequestBody SensorStation station) {
        SensorStation created = stationService.createStation(station);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "SensorStation created successfully.",
                "station", created
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'ENVIRONMENTAL_OFFICER')")
    public ResponseEntity<Object> updateStation(@PathVariable Long id, @Valid @RequestBody SensorStation station) {
        SensorStation updated = stationService.updateStation(id, station);
        return ResponseEntity.ok(Map.of(
                "message", "SensorStation updated successfully.",
                "station", updated
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'ENVIRONMENTAL_OFFICER')")
    public ResponseEntity<String> deleteStation(@PathVariable Long id) {
        stationService.deleteStation(id);
        return ResponseEntity.ok("SensorStation deleted successfully.");
    }
}
