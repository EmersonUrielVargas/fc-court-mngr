package com.foodcourt.court.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateItemOrderRequestDto {
    @NotNull
    private Integer quantity;
    @NotNull
    private Long plateId;
}