package com.product.management.controller;

import com.product.management.payload.JwtAuthResponse;
import com.product.management.payload.LoginDto;
import com.product.management.payload.RegisterDto;
import com.product.management.payload.RegisterResponseDto;
import com.product.management.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<JwtAuthResponse> authenticate(@RequestBody LoginDto loginDto) {
        String token = userService.login(loginDto);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/add")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterDto registerDto) {
        RegisterResponseDto response = userService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
