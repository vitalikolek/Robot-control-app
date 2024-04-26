package com.microservice.robot.controller;

import com.microservice.robot.request.AuthenticationRequest;
import com.microservice.robot.request.RegisterRequest;
import com.microservice.robot.response.AuthenticationResponse;
import com.microservice.robot.service.AuthenticationService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("${application.security.jwt.auth-base-url}")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AuthenticationController {
    @Value("${application.security.jwt.access-token-header}")
    private String accessTokenHeader;

    @Value("${application.security.jwt.refresh-token-header}")
    private String refreshTokenHeader;

    private final AuthenticationService service;
    private final Bucket bucket;

    @Autowired
    public AuthenticationController(AuthenticationService service) {
        log.info("AuthenticationController created");
        this.service = service;
        Bandwidth limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @PostMapping("/sign-up/create-account")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        AuthenticationResponse response = service.register(request);
        HttpHeaders headers = setHttpHeaders(response);
        return ResponseEntity.ok().headers(headers).build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse response = service.authenticate(request);
        HttpHeaders headers = setHttpHeaders(response);
        return ResponseEntity.ok().headers(headers).build();
    }

    @NonNull
    private HttpHeaders setHttpHeaders(AuthenticationResponse response) {
        HttpHeaders headers = new HttpHeaders();
        String bearer = "Bearer ";
        headers.add(accessTokenHeader, bearer + response.getAccessToken());
        headers.add(refreshTokenHeader, response.getRefreshToken());
        return headers;
    }

}
