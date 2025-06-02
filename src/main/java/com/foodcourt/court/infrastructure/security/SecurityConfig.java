package com.foodcourt.court.infrastructure.security;

import com.foodcourt.court.domain.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private static final String[] PATHS_ALL_ALLOW = {
            "/public/**", "/swagger-ui/**", "/swagger-ui.**", "/v3/api-docs/**"
    };

    private static final String[] PATHS_GET_CLIENT = {
            "/v1/restaurant", "/v1/restaurant/*/plates"
    };

    private static final String[] PATHS_EMPLOYEE = {
            "/v1/restaurant/*/orders"
    };

    private static final String[] PATHS_OWNER = {
            "/v1/plate/**", "/v1/restaurant/assignment"
    };

    private static final String[] PATHS_ADMIN = {
            "/v1/restaurant/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers(PATHS_ALL_ALLOW).permitAll()
                    .requestMatchers(HttpMethod.GET, PATHS_GET_CLIENT).hasAuthority(UserRole.CLIENT.name())
                    .requestMatchers(HttpMethod.GET, PATHS_EMPLOYEE).hasAuthority(UserRole.EMPLOYEE.name())
                    .requestMatchers(PATHS_OWNER).hasAuthority(UserRole.OWNER.name())
                    .requestMatchers(PATHS_ADMIN).hasAuthority(UserRole.ADMIN.name())
                    .anyRequest()
                    .authenticated()
            ).sessionManagement(sessionManageConfig ->
                sessionManageConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
