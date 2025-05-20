package com.foodcourt.court.domain.spi;

import com.foodcourt.court.domain.model.Plate;

public interface IPlatePersistencePort {
    void createPlate(Plate plate);
}
