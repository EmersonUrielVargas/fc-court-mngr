package com.foodcourt.court.infrastructure.out.jpa.adapter;

import com.foodcourt.court.domain.model.Plate;
import com.foodcourt.court.domain.spi.IPlatePersistencePort;
import com.foodcourt.court.infrastructure.out.jpa.entity.PlateEntity;
import com.foodcourt.court.infrastructure.out.jpa.mapper.IPlateEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.repository.IPlateRepository;
import com.foodcourt.court.infrastructure.shared.GeneralConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
    public List<Plate> getPlatesByRestaurantId(Long restaurantId, Integer pageSize, Integer page) {
        return plateRepository.findByRestaurant(
                        restaurantId,
                        PageRequest.of(page, pageSize, Sort.by(GeneralConstants.FIELD_NAME).ascending())
                )
                .stream()
                .map(plateEntityMapper::toPlate)
                .toList();
    }

    @Override
    public List<Plate> getPlatesByRestaurantId(Long restaurantId, Integer pageSize, Integer page, Long categoryId) {
        return plateRepository.findByRestaurantAndCategory(
                        restaurantId,
                        categoryId,
                        PageRequest.of(page, pageSize, Sort.by(GeneralConstants.FIELD_NAME).ascending())
                )
                .stream()
                .map(plateEntityMapper::toPlate)
                .toList();
    }
}
