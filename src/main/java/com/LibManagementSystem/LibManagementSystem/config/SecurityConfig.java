package com.LibManagementSystem.LibManagementSystem.config;

import com.LibManagementSystem.LibManagementSystem.models.UserRole;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                            //permit auth routes
                            auth.requestMatchers(
                                    new AntPathRequestMatcher("/api/v1/auth/**")
                            ).permitAll();


                            //specify role privileges for content
                            auth.requestMatchers(new AntPathRequestMatcher("/api/v1/admin/**")).hasRole(
                                    UserRole.ADMIN.name()
                            );

                            auth.requestMatchers(new AntPathRequestMatcher("/api/v1/user/**")).
                                    hasAnyRole(UserRole.ADMIN.name(), UserRole.USER.name());
                            auth.anyRequest().authenticated();

                        }
                );

        //stateless when working w jwt
        http.sessionManagement(session ->
                session.sessionCreationPolicy(STATELESS));

        //insert our custom filter

        http.authenticationProvider(authenticationProvider).addFilterBefore(jwtAuthFilter,
                UsernamePasswordAuthenticationFilter.class);


        //once this route is visited all routes are reset and user will have to re-authenticate again
        http.logout(logout ->
                logout.logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
        );
        return http.build();
    }
}
