package com.krabelard.safeapp.controller;

import com.krabelard.safeapp.dto.CredentialsDTO;
import com.krabelard.safeapp.dto.LoginRequestDTO;
import com.krabelard.safeapp.dto.RegisterResponseDTO;
import com.krabelard.safeapp.exception.user.EmailTakenException;
import com.krabelard.safeapp.exception.user.UserNotFoundException;
import com.krabelard.safeapp.exception.user.UsernameTakenException;
import com.krabelard.safeapp.model.User;
import com.krabelard.safeapp.repository.UserRepository;
import com.krabelard.safeapp.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<CredentialsDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        val auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        val jwt = jwtService.generateToken(auth);
        val response = CredentialsDTO.builder()
                .username(request.username())
                .email(
                        userRepository.findByUsername(request.username())
                                .orElseThrow(() -> new UserNotFoundException(request.username()))
                                .getEmail()
                )
                .credential(jwt)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody CredentialsDTO request) {
        val username = request.username();
        val email = request.email();
        val password = request.credential();

        checkForConflict(username, email);

        val user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        userRepository.save(user);

        val response = RegisterResponseDTO.builder()
                .username(username)
                .email(email)
                .build();

        return ResponseEntity.ok(response);
    }

    private void checkForConflict(String username, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameTakenException(username);
        }
        if (userRepository.existsByEmail(email)) {
            throw new EmailTakenException(email);
        }
    }

}
