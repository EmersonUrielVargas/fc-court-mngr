package com.foodcourt.court.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Plate {
    private Long id;
    private String name;
    private Long categoryId;
    private String description;
    private Integer price;
    private Long restaurantId;
    private String urlImage;

    @Builder.Default
    private Boolean isActive = true;
}
