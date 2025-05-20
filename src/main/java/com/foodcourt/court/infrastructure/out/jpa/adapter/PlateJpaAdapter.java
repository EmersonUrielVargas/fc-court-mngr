package com.foodcourt.court.infrastructure.out.jpa.adapter;

import com.foodcourt.court.domain.model.Plate;
import com.foodcourt.court.domain.model.Restaurant;
import com.foodcourt.court.domain.spi.IPlatePersistencePort;
import com.foodcourt.court.domain.spi.IRestaurantPersistencePort;
import com.foodcourt.court.infrastructure.out.jpa.entity.PlateEntity;
import com.foodcourt.court.infrastructure.out.jpa.mapper.IPlateEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.mapper.IRestaurantEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.repository.IPlateRepository;
import com.foodcourt.court.infrastructure.out.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlateJpaAdapter implements IPlatePersistencePort {

    private final IPlateRepository plateRepository;
    private final IPlateEntityMapper plateEntityMapper;


    @Override
    public void createPlate(Plate plate) {
        PlateEntity plateEntity = plateEntityMapper.toPlateEntity(plate);
        plateRepository.save(plateEntity);
    }
}
