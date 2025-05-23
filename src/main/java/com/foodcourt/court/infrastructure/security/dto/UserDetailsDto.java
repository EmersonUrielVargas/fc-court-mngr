package com.foodcourt.court.infrastructure.security.dto;

import com.foodcourt.court.domain.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
public class UserDetailsDto implements UserDetails {

    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private Long idUser;
    @NotNull
    private UserRole role;
    @NotNull
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
