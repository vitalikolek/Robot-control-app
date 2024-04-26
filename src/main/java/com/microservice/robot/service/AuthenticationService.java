package com.microservice.robot.service;

import com.microservice.robot.config.JwtService;
import com.microservice.robot.data.RefreshToken;
import com.microservice.robot.data.User;
import com.microservice.robot.persistence.RefreshTokenRepository;
import com.microservice.robot.persistence.UserRepository;
import com.microservice.robot.request.AuthenticationRequest;
import com.microservice.robot.request.RegisterRequest;
import com.microservice.robot.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private void saveUserToken(User user, String jwtToken) {
        var token = RefreshToken.builder().userId(user.getId()).token(jwtToken).expiryDate(Instant.now().plusMillis(jwtService.getRefreshExpiration())).build();
        refreshTokenRepository.save(token);
        log.info("User`s token have been saved");
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder().firstname(request.getFirstname()).lastname(request.getLastname()).email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).build();
        repository.save(user);
        log.info("User {} registered", user.getId());
        return generateTokens(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, refreshToken);
        log.info("User {} authenticated", user.getId());
        return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    private AuthenticationResponse generateTokens(User user) {
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, refreshToken);
        log.info("Tokens have been generated for User {}", user.getId());
        return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = refreshTokenRepository.findAllByUserId((user.getId()));
        if (validUserTokens.isEmpty()) return;
        log.info("All tokens user {} have been deleted", user.getId());
        refreshTokenRepository.deleteAll(validUserTokens);
    }
}
