package com.foodcourt.court.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreatePlateRequestDto {
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
    @NotNull
    private Long restaurantId;
}