package com.example.demo.controller;

import com.example.demo.dto.ObservationDto;
import com.example.demo.entity.HealthObservation;
import com.example.demo.entity.SystemUser;
import com.example.demo.service.HealthTrackingService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
public class HealthLogController {
    private final HealthTrackingService healthTrackingService;

    @GetMapping
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<List<HealthObservation>> history(@AuthenticationPrincipal SystemUser user) {
        return ResponseEntity.ok(healthTrackingService.getUserHistory(user));
    }

    @PostMapping
    @PreAuthorize("hasRole('CITIZEN')")
    public ResponseEntity<HealthObservation> logSymptoms(
            @AuthenticationPrincipal SystemUser user,
            @Valid @RequestBody ObservationDto request
    ) {
        return ResponseEntity.ok(healthTrackingService.logSymptoms(user, request));
    }
}
