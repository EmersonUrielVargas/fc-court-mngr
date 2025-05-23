package com.foodcourt.court.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StatusPlateRequestDto {
    @NotNull
    private Long id;

    @NotNull
    private Boolean isActive;
}