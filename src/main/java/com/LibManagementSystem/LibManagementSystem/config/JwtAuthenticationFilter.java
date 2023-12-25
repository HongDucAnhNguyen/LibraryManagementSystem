package com.LibManagementSystem.LibManagementSystem.config;

import com.LibManagementSystem.LibManagementSystem.repo.TokenRepo;
import com.LibManagementSystem.LibManagementSystem.service.AuthManagementService.JwtService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Configuration
@RequiredArgsConstructor
//custom filter
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepo tokenRepo;

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain)

            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        //if token is present, subtract token content after "BEARER "
        jwt = authHeader.substring(7);

        //extract user email from token
        userEmail = jwtService.extractUserEmail(jwt);


        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //if user has not been authenticated


            //retrieve user details from the database
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
//            validate token
            boolean isTokenNewestToken;
            var tokenFound = tokenRepo.findByToken(jwt).get();
            if (!tokenFound.isRevoked() && !tokenFound.isExpired()) {
                isTokenNewestToken = true;
            } else isTokenNewestToken = false;



            if (jwtService.isTokenValid(jwt, userDetails) && isTokenNewestToken) {
                //if token is valid, set authentication context
                //authToken contains the roles of this user so filterchain can filter
                //users that do not have permission for certain endpoints
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()

                );
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }

        }

        filterChain.doFilter(request, response);
    }
}
