package com.example.demo.repository;

import com.example.demo.entity.RespiratoryProfile;
import com.example.demo.entity.SystemUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespiratoryProfileRepository extends JpaRepository<RespiratoryProfile, Long> {
    Optional<RespiratoryProfile> findByUser(SystemUser user);
}
