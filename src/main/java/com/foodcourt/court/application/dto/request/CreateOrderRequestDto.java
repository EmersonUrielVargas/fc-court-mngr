package com.foodcourt.court.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class CreateOrderRequestDto {
    @NotNull
    private List<CreateItemOrderRequestDto> orderPlates;
    @NotNull
    private Long restaurantId;
}