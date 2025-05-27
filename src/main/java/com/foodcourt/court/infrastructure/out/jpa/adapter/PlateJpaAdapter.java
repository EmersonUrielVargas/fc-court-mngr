package com.foodcourt.court.infrastructure.out.jpa.adapter;

import com.foodcourt.court.domain.model.Plate;
import com.foodcourt.court.domain.spi.IPlatePersistencePort;
import com.foodcourt.court.infrastructure.out.jpa.entity.PlateEntity;
import com.foodcourt.court.infrastructure.out.jpa.mapper.IPlateEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.repository.IPlateRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PlateJpaAdapter implements IPlatePersistencePort {

    private final IPlateRepository plateRepository;
    private final IPlateEntityMapper plateEntityMapper;

    @Override
    public void upsertPlate(Plate plate) {
        PlateEntity plateEntity = plateEntityMapper.toPlateEntity(plate);
        plateRepository.save(plateEntity);
    }

    @Override
    public Optional<Plate> getByID(Long plateId) {
        return plateRepository.findById(plateId).map(plateEntityMapper::toPlate);
    }

    @Override
    public List<Plate> getPlatesByRestaurantIdByCategoryId(Long restaurantId, Integer pageSize, Integer page, Long categoryId) {
        return List.of();
    }
}
