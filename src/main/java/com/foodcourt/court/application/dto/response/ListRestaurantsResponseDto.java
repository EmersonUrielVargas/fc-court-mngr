package com.foodcourt.court.application.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListRestaurantsResponseDto {
    @NotNull
    private String name;
    @NotNull
    private String urlLogo;
}
