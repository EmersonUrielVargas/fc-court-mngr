package com.foodcourt.court.application.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RestaurantItemResponseDto {
    @NotNull
    private String name;
    @NotNull
    private String urlLogo;
}
