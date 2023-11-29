package com.LibManagementSystem.LibManagementSystem.controller;

import com.LibManagementSystem.LibManagementSystem.DTO.AuthResponse;
import com.LibManagementSystem.LibManagementSystem.DTO.LoginRequestDTO;
import com.LibManagementSystem.LibManagementSystem.DTO.RegisterRequestDTO;
import com.LibManagementSystem.LibManagementSystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequestDTO request){
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequestDTO request){
        return ResponseEntity.ok(authService.login(request));
    }

}
