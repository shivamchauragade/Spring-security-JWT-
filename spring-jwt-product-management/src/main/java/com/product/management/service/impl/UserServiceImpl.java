package com.product.management.service.impl;


import com.product.management.entity.User;
import com.product.management.exception.CustomException;
import com.product.management.payload.LoginDto;
import com.product.management.payload.RegisterDto;
import com.product.management.payload.RegisterResponseDto;
import com.product.management.repository.UserRepository;
import com.product.management.security.JwtTokenProvider;
import com.product.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUserName(), loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    @Override
    public RegisterResponseDto register(RegisterDto registerDto) {

        // Check username already exists or not
        if (userRepository.existsByUsername(registerDto.getUserName())) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        User user = new User();
        user.setUsername(registerDto.getUserName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(user);
        RegisterResponseDto responseDto = new RegisterResponseDto();
        responseDto.setUserName(user.getUsername());
        responseDto.setPassword(registerDto.getPassword());
        return responseDto;
    }

    private String generateToken() {
        StringBuilder token = new StringBuilder();

        return token.append(UUID.randomUUID())
                .append(UUID.randomUUID()).toString();
    }

    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

        Duration tokenValidityDuration = Duration.ofMinutes(1); // Adjust the duration as needed
        boolean isExpired = isExpired(tokenCreationDate, tokenValidityDuration);
        if (isExpired) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isExpired(final LocalDateTime tokenCreationDate, final Duration tokenValidityDuration) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tokenExpirationDate = tokenCreationDate.plus(tokenValidityDuration);
        return now.isAfter(tokenExpirationDate);
    }
}

