package com.foodcourt.court.domain.spi;

import com.foodcourt.court.domain.model.Plate;

import java.util.Optional;

public interface IPlatePersistencePort {
    void createPlate(Plate plate);

    void updatePlate(Plate plate);

    Optional<Plate> getByID(Long plateId);


}
