package com.foodcourt.court.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdatePlateRequestDto {
    @NotNull
    private Long id;

    private Integer price;
    private String description;
}