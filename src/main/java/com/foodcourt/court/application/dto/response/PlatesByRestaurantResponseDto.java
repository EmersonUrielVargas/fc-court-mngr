package com.foodcourt.court.application.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PlatesByRestaurantResponseDto {
    @NotNull
    private String name;
    @NotNull
    private Integer price;
    @NotNull
    private Long categoryId;
    @NotNull
    private String description;
    @NotNull
    private String urlImage;
}
