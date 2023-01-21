package com.krabelard.safeapp.service;


import com.krabelard.safeapp.dto.LoginRequestDTO;
import com.krabelard.safeapp.dto.LoginResponseDTO;
import com.krabelard.safeapp.dto.RegisterRequestDTO;
import com.krabelard.safeapp.dto.RegisterResponseDTO;
import com.krabelard.safeapp.exception.user.EmailTakenException;
import com.krabelard.safeapp.exception.user.UserNotFoundException;
import com.krabelard.safeapp.exception.user.UsernameTakenException;
import com.krabelard.safeapp.model.User;
import com.krabelard.safeapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public RegisterResponseDTO register(RegisterRequestDTO dto) {
        val username = dto.username();
        val email = dto.email();
        val password = dto.password();

        checkForConflict(username, email);

        val user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        userRepository.save(user);

        return RegisterResponseDTO.builder()
                .username(username)
                .email(email)
                .build();
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        val auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        val jwt = jwtService.generateToken(auth);

        return LoginResponseDTO.builder()
                .username(dto.username())
                .email(
                        userRepository.findByUsername(dto.username())
                                .orElseThrow(() -> new UserNotFoundException(dto.username()))
                                .getEmail()
                )
                .jwt(jwt)
                .build();
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
