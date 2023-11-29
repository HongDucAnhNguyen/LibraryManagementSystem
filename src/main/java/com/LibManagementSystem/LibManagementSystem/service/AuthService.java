package com.LibManagementSystem.LibManagementSystem.service;

import com.LibManagementSystem.LibManagementSystem.DTO.AuthResponse;
import com.LibManagementSystem.LibManagementSystem.DTO.LoginRequestDTO;
import com.LibManagementSystem.LibManagementSystem.DTO.RegisterRequestDTO;
import com.LibManagementSystem.LibManagementSystem.models.Token;
import com.LibManagementSystem.LibManagementSystem.models.TokenType;
import com.LibManagementSystem.LibManagementSystem.models.User;
import com.LibManagementSystem.LibManagementSystem.models.UserRole;
import com.LibManagementSystem.LibManagementSystem.repo.TokenRepo;
import com.LibManagementSystem.LibManagementSystem.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    private void revokeAllUserToken(User user) {
        var validTokensByUser = tokenRepo.findAllValidTokensByUser(user.getUserId());
        if (validTokensByUser.isEmpty()) {
            return;
        }
        validTokensByUser.forEach(token -> {

            token.setExpired(true);
            token.setRevoked(true);

        });


        tokenRepo.saveAll(validTokensByUser);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder().user(user)
                .token(jwtToken).tokenType(TokenType.BEARER).revoked(false).expired(false).build();
        tokenRepo.save(token);

    }

    public AuthResponse register(RegisterRequestDTO request) {
        User user = User.builder().firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .userRole(UserRole.USER).build();
        userRepo.save(user);

        String jwtToken = jwtService.generateToken(user);

        revokeAllUserToken(user);
        saveUserToken(user, jwtToken);
        return AuthResponse.builder().token(jwtToken).build();
    }

    public AuthResponse login(LoginRequestDTO request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        ));

        User userRetrieved = userRepo.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(userRetrieved);

        revokeAllUserToken(userRetrieved);
        saveUserToken(userRetrieved, jwtToken);


        return AuthResponse.builder().token(jwtToken).build();

    }


}
