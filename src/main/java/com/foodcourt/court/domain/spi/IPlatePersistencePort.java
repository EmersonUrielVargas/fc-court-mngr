package com.foodcourt.court.domain.spi;

import com.foodcourt.court.domain.model.Plate;

import java.util.Optional;

public interface IPlatePersistencePort {

    void upsertPlate(Plate plate);

    Optional<Plate> getByID(Long plateId);


}
