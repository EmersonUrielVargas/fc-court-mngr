package com.foodcourt.court.infrastructure.security.service;

import com.foodcourt.court.infrastructure.security.dto.UserDetailsDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AutheticationService {

    public Long getUserId(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetailsDto)auth.getPrincipal()).getIdUser();
    }
}
