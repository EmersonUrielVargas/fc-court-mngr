package com.foodcourt.court.domain.model;

import com.foodcourt.court.domain.enums.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private Long clientId;
    private LocalDateTime date;
    private Long chefId;
    private Long restaurantId;
    private Integer codeValidation;
    private List<OrderPlate> orderPlates;

    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;
}
