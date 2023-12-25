package com.LibManagementSystem.LibManagementSystem.service.AuthManagementService;

import com.LibManagementSystem.LibManagementSystem.DTO.responses.AccountResponse;
import com.LibManagementSystem.LibManagementSystem.DTO.responses.AuthResponse;
import com.LibManagementSystem.LibManagementSystem.models.UserRelated.User;
import com.LibManagementSystem.LibManagementSystem.repo.TokenRepo;
import com.LibManagementSystem.LibManagementSystem.repo.UserRepo;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class IndividualAccountService {
    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public User updateMyAccountService(Integer userId, User updateData, String confirmPassword) {


        return null;

    }

    public AccountResponse getAccountDetails(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader("Authorization");
        final String accessToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            return AccountResponse.builder().resMessage("no tokens provided").build();
        }
        accessToken = authHeader.substring(7);


        userEmail = jwtService.extractUserEmail(accessToken);
        if (userEmail == null) {
            return AccountResponse.builder().resMessage("token does not contain necessary data").build();
        }

        var userRetrieved = this.userRepo.findByEmail(userEmail).orElseThrow();
        if (userRetrieved == null) {
            return AccountResponse.builder().resMessage("data does not exist for account").build();

        }

        return AccountResponse.builder().accountEmail(userRetrieved.getEmail()).
                accountName(userRetrieved.getFirstName() + " " + userRetrieved.getLastName()).build();


    }
}
