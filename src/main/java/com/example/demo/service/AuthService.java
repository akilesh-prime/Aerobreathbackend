package com.example.demo.service;

import com.example.demo.dto.AuthResponseDto;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.SystemUser;
import com.example.demo.exception.BusinessValidationException;
import com.example.demo.repository.SystemUserRepository;
import com.example.demo.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final SystemUserRepository userRepository;
    private final JwtService jwtService;

    public AuthResponseDto login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));
        SystemUser user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        return new AuthResponseDto(jwtService.generateToken(user), user.getUsername(), user.getRole().name(), user.getCity());
    }

    public AuthResponseDto register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessValidationException("Username is already registered.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessValidationException("Email is already registered.");
        }

        SystemUser user = new SystemUser();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setCity(request.getCity());
        user.setRole(request.getRole());
        user.setPassword(request.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        SystemUser saved = userRepository.save(user);
        return new AuthResponseDto(jwtService.generateToken(saved), saved.getUsername(), saved.getRole().name(), saved.getCity());
    }
}
