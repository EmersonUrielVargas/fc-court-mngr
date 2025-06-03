package com.foodcourt.court.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UpdateStatusOrderRequestDto {
    @NotNull
    private Long id;

    @NotNull
    private String status;

    private String clientCode;
}