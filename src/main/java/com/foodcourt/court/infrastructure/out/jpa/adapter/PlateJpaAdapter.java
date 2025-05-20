package com.foodcourt.court.infrastructure.out.jpa.adapter;

import com.foodcourt.court.domain.model.Plate;
import com.foodcourt.court.domain.spi.IPlatePersistencePort;
import com.foodcourt.court.infrastructure.exception.NoDataFoundException;
import com.foodcourt.court.infrastructure.out.jpa.entity.PlateEntity;
import com.foodcourt.court.infrastructure.out.jpa.mapper.IPlateEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.repository.IPlateRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class PlateJpaAdapter implements IPlatePersistencePort {

    private final IPlateRepository plateRepository;
    private final IPlateEntityMapper plateEntityMapper;


    @Override
    public void createPlate(Plate plate) {
        PlateEntity plateEntity = plateEntityMapper.toPlateEntity(plate);
        plateRepository.save(plateEntity);
    }

    @Override
    public void updatePlate(Plate plate) {
        plateRepository.findById(plate.getId()).orElseThrow(NoDataFoundException::new);
        PlateEntity plateEntity = plateEntityMapper.toPlateEntity(plate);
        plateRepository.save(plateEntity);
    }

    @Override
    public Optional<Plate> getByID(Long plateId) {
        return plateRepository.findById(plateId).map(plateEntityMapper::toPlate);
    }
}
