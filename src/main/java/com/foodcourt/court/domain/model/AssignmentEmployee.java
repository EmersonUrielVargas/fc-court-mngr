package com.foodcourt.court.domain.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AssignmentEmployee {
    private Long restaurantId;
    private Long employeeId;
}
