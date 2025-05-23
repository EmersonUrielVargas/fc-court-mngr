package com.foodcourt.court.infrastructure.security;

import com.foodcourt.court.domain.enums.UserRole;
import com.foodcourt.court.infrastructure.security.dto.UserDetailsDto;
import com.foodcourt.court.infrastructure.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTH_HEADER_NAME = "Authorization";
    public static final String AUTH_TOKEN_PREFIX = "Bearer ";
    public static final String ROL_KEY_TOKEN = "role";
    public static final String USER_ID_KEY_TOKEN = "userID";


    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(AUTH_HEADER_NAME);
        final String jwtToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith(AUTH_TOKEN_PREFIX)){
            filterChain.doFilter(request,response);
            return;
        }
        jwtToken = authHeader.replace(AUTH_TOKEN_PREFIX,"");
        userEmail = jwtService.extractUserEmail(jwtToken);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = buildUserDetails(jwtToken, userEmail);
            if (jwtService.isTokenValid(jwtToken)){
                UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                userAuthToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(userAuthToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private UserDetails buildUserDetails(String token, String email){
        String roleFromToken = jwtService.extractClaim(
                token,
                claims->claims.get(ROL_KEY_TOKEN, String.class)
        );
        Long userId = jwtService.extractClaim(
                token,
                claims->claims.get(USER_ID_KEY_TOKEN, Long.class)
        );
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(roleFromToken));
        return UserDetailsDto.builder()
                .idUser(userId)
                .role(UserRole.valueOf(roleFromToken))
                .email(email)
                .authorities(authorities)
                .build();
    }
}
