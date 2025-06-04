package com.foodcourt.court.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackingOrder {
    private Long orderId;
    private Long clientId;
    private String clientEmail;
    private LocalDateTime date;
    private Long employeeId;
    private String previousStatus;
    private String nextStatus;
    private String employeeEmail;
}
