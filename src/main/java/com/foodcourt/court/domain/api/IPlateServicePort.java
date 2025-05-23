package com.foodcourt.court.domain.api;

import com.foodcourt.court.domain.model.Plate;

public interface IPlateServicePort {
    void create(Plate plate, Long ownerId);

    void update(Plate plate, Long ownerId);
    void setActive(Plate plate, Long ownerId);
}
