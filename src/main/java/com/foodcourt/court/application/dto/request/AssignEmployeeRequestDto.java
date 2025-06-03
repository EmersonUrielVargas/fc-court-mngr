package com.foodcourt.court.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class AssignEmployeeRequestDto {
    @NotNull
    private Long idEmployee;
}
