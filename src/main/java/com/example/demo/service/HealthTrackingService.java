package com.example.demo.service;

import com.example.demo.dto.ObservationDto;
import com.example.demo.entity.ConditionType;
import com.example.demo.entity.HealthObservation;
import com.example.demo.entity.RespiratoryProfile;
import com.example.demo.entity.SystemUser;
import com.example.demo.repository.HealthObservationRepository;
import com.example.demo.repository.RespiratoryProfileRepository;
import com.example.demo.repository.SystemUserRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HealthTrackingService {
    private final SystemUserRepository userRepository;
    private final RespiratoryProfileRepository profileRepository;
    private final HealthObservationRepository observationRepository;

    @Transactional
    public HealthObservation logSymptoms(SystemUser userPrincipal, ObservationDto request) {
        SystemUser user = userRepository.findByUsername(userPrincipal.getUsername()).orElseThrow();
        RespiratoryProfile profile = profileRepository.findByUser(user).orElseGet(() -> {
            RespiratoryProfile created = new RespiratoryProfile();
            created.setUser(user);
            created.setConditionType(ConditionType.NONE);
            return profileRepository.save(created);
        });

        HealthObservation observation = new HealthObservation();
        observation.setProfile(profile);
        observation.setSymptomScore(request.getSymptomScore());
        observation.setNotes(request.getNotes());
        observation.setActivityLevel(request.getActivityLevel());
        observation.setRecordedAt(LocalDateTime.now());
        return observationRepository.save(observation);
    }

    @Transactional(readOnly = true)
    public List<HealthObservation> getUserHistory(SystemUser userPrincipal) {
        SystemUser user = userRepository.findByUsername(userPrincipal.getUsername()).orElseThrow();
        return observationRepository.findByProfileUserOrderByRecordedAtDesc(user);
    }
}
