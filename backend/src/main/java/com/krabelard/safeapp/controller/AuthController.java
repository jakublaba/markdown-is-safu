package com.krabelard.safeapp.controller;

import com.krabelard.safeapp.dto.CredentialsDTO;
import com.krabelard.safeapp.dto.LoginRequestDTO;
import com.krabelard.safeapp.dto.RegisterResponseDTO;
import com.krabelard.safeapp.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // since sessions are stateless - it returns username only if bearer token is passed in Authorization header
    @GetMapping("/whoami")
    public ResponseEntity<String> whoami() {
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody CredentialsDTO request) {
        return new ResponseEntity<>(
                authService.register(request),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<CredentialsDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }


}
