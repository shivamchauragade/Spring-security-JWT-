package com.product.management.service;

import com.product.management.payload.LoginDto;
import com.product.management.payload.RegisterDto;
import com.product.management.payload.RegisterResponseDto;

public interface UserService {
    String login(LoginDto loginDto);

    RegisterResponseDto register(RegisterDto registerDto);

}
