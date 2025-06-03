package com.foodcourt.court.domain.model;

import com.foodcourt.court.domain.enums.UserRole;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private UserRole rol;
    private String email;
    private String phoneNumber;
    private String name;
}
