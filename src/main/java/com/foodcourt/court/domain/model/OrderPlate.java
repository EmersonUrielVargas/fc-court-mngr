package com.foodcourt.court.domain.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class OrderPlate {
    private Integer quantity;
    private Long orderId;
    private Long plateId;
}
