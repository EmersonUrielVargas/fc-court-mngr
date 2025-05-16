package com.foodcourt.court.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RestaurantRequestDto {
    @NotNull
    private String name;
    @NotNull
    private String address;
    @NotNull
    private Long ownerId;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String urlLogo;
    @NotNull
    private String nit;
}
