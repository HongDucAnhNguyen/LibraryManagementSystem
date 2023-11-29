package com.LibManagementSystem.LibManagementSystem.service;

import com.LibManagementSystem.LibManagementSystem.repo.TokenRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogOutService implements LogoutHandler {
    private final TokenRepo tokenRepo;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//extract the auth part of the request header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer")) {

            return;
        }

        //if bearer token is present
        jwt = authHeader.substring(7);
        var storedToken = tokenRepo.findByToken(jwt).orElse(null);

        //make token invalid and clear auth context
        if(storedToken != null){
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepo.save(storedToken);
        }

    }
}
