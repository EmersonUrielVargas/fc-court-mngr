package com.foodcourt.court.application.dto.response;

import com.foodcourt.court.domain.model.OrderPlate;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
public class GetOrderResponseDto {
    @NotNull
    private List<OrderPlate> orderPlates;
    @NotNull
    private Long restaurantId;
    @NotNull
    private Long id;
    @NotNull
    private Long clientId;
    @NotNull
    private LocalDateTime date;
    @NotNull
    private Long chefId;
    @NotNull
    private String status;
}