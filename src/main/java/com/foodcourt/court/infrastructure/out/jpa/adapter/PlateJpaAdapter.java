package com.foodcourt.court.infrastructure.out.jpa.adapter;

import com.foodcourt.court.domain.model.Plate;
import com.foodcourt.court.domain.spi.IPlatePersistencePort;
import com.foodcourt.court.domain.utilities.CustomPage;
import com.foodcourt.court.infrastructure.out.jpa.entity.PlateEntity;
import com.foodcourt.court.infrastructure.out.jpa.mapper.IPlateEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.repository.IPlateRepository;
import com.foodcourt.court.infrastructure.shared.GeneralConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public CustomPage<Plate> getPlatesByRestaurantId(Long restaurantId, Integer pageSize, Integer page) {
        Page<PlateEntity> platesEntity = plateRepository.findByRestaurant(
                restaurantId,
                PageRequest.of(page, pageSize, Sort.by(GeneralConstants.FIELD_NAME).ascending())
        );
        CustomPage<Plate> plates = new CustomPage<>();
        plates.setData(plateEntityMapper.toPlate(platesEntity.getContent()));
        plates.setCurrentPage(platesEntity.getNumber());
        plates.setPageSize(platesEntity.getSize());
        plates.setTotalPages(platesEntity.getTotalPages());
        plates.setTotalItems(platesEntity.getTotalElements());
        plates.setIsLastPage(platesEntity.isLast());
        return plates;
    }

    @Override
    public CustomPage<Plate> getPlatesByRestaurantId(Long restaurantId, Integer pageSize, Integer page, Long categoryId) {
        Page<PlateEntity> platesEntity = plateRepository.findByRestaurantAndCategory(
                restaurantId,
                categoryId,
                PageRequest.of(page, pageSize, Sort.by(GeneralConstants.FIELD_NAME).ascending())
        );
        CustomPage<Plate> plates = new CustomPage<>();
        plates.setData(plateEntityMapper.toPlate(platesEntity.getContent()));
        plates.setCurrentPage(platesEntity.getNumber());
        plates.setPageSize(platesEntity.getSize());
        plates.setTotalPages(platesEntity.getTotalPages());
        plates.setTotalItems(platesEntity.getTotalElements());
        plates.setIsLastPage(platesEntity.isLast());
        return plates;
    }

    @Override
    public List<Long> findExistingPlateIdsInRestaurant(Long restaurantId, List<Long> plateIds) {
        return plateRepository.findExistingPlateIdsInRestaurant(restaurantId, plateIds);
    }
}
