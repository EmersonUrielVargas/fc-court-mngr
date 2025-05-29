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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers("/public/**", "/swagger-ui/**", "/swagger-ui.**", "/v3/api-docs/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/v1/restaurant", "/v1/restaurant/*/plates").hasAuthority(UserRole.CLIENT.name())
                    .requestMatchers(HttpMethod.GET, "/v1/restaurant/*/orders").permitAll()
                    .requestMatchers("/v1/plate/**").hasAuthority(UserRole.OWNER.name())
                    .requestMatchers("/v1/restaurant/**").hasAuthority(UserRole.ADMIN.name())
                    .anyRequest()
                    .authenticated()
            ).sessionManagement(sessionManageConfig ->
                sessionManageConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
