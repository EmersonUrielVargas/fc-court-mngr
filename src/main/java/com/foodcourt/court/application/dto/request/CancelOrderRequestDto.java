package com.foodcourt.court.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CancelOrderRequestDto {
    @NotNull
    private Long id;
}