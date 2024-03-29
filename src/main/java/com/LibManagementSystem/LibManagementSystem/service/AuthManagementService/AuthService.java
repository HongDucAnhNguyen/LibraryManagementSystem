package com.LibManagementSystem.LibManagementSystem.service.AuthManagementService;

import com.LibManagementSystem.LibManagementSystem.DTO.responses.AuthResponse;
import com.LibManagementSystem.LibManagementSystem.DTO.requests.AuthRelated.LoginRequestDTO;
import com.LibManagementSystem.LibManagementSystem.DTO.requests.AuthRelated.RegisterRequestDTO;
import com.LibManagementSystem.LibManagementSystem.models.JwtTokenRelated.Token;
import com.LibManagementSystem.LibManagementSystem.models.JwtTokenRelated.TokenType;
import com.LibManagementSystem.LibManagementSystem.models.UserRelated.User;
import com.LibManagementSystem.LibManagementSystem.models.UserRelated.UserRole;
import com.LibManagementSystem.LibManagementSystem.repo.TokenRepo;
import com.LibManagementSystem.LibManagementSystem.repo.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
                .userRole(UserRole.USER).userEnabled(true).build();
        userRepo.save(user);

        String jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserToken(user);
        saveUserToken(user, jwtToken);
        return AuthResponse.builder().token(jwtToken).refreshToken(refreshToken).build();
    }

    public AuthResponse login(LoginRequestDTO request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        ));

        User userRetrieved = userRepo.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(userRetrieved);
        var refreshToken = jwtService.generateRefreshToken(userRetrieved);
        revokeAllUserToken(userRetrieved);
        saveUserToken(userRetrieved, jwtToken);


        return AuthResponse.builder().token(jwtToken).refreshToken(refreshToken).build();

    }


    public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader("Authorization");
        //the refresh-token endpoint requires the refreshtoken to be in auth header
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            //if there is no bearer jwt token provided within the request,
            return;
        }

        //if bearer token is present
        refreshToken = authHeader.substring(7);
        //start validating the token by extracting the user email from jwt token
        //to extract, go through the service layer
        userEmail = jwtService.extractUserEmail(refreshToken);
        if (userEmail != null) {

            //retrieve user by user email
            var userRetrieved = this.userRepo.findByEmail(userEmail).orElseThrow();


            if (jwtService.isTokenValid(refreshToken, userRetrieved)) {
                var newAccessToken = jwtService.generateToken(userRetrieved);
                revokeAllUserToken(userRetrieved);
                saveUserToken(userRetrieved, newAccessToken);
                var authReponse = AuthResponse.builder().token(newAccessToken)
                        .refreshToken(refreshToken).build();
                new ObjectMapper().writeValue(response.getOutputStream(), authReponse);

            }
        }


    }
}
