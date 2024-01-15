package com.LibManagementSystem.LibManagementSystem.controller.AuthManagementController;

import com.LibManagementSystem.LibManagementSystem.DTO.responses.AuthResponse;
import com.LibManagementSystem.LibManagementSystem.DTO.requests.AuthRelated.LoginRequestDTO;
import com.LibManagementSystem.LibManagementSystem.DTO.requests.AuthRelated.RegisterRequestDTO;
import com.LibManagementSystem.LibManagementSystem.service.AuthManagementService.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @NonNull RegisterRequestDTO request){
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @NonNull LoginRequestDTO request){
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("/refresh-token")
    public void refreshAccessToken(
            HttpServletRequest request, HttpServletResponse response

    ) throws IOException {
        authService.refreshAccessToken(request, response);
    }
}
