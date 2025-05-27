package com.foodcourt.court.infrastructure.out.jpa.adapter;

import com.foodcourt.court.domain.model.Plate;
import com.foodcourt.court.infrastructure.exception.NoDataFoundException;
import com.foodcourt.court.infrastructure.out.jpa.entity.PlateEntity;
import com.foodcourt.court.infrastructure.out.jpa.mapper.IPlateEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.repository.IPlateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlateJpaAdapterTest {

    @Mock
    private IPlateRepository plateRepository;

    @Mock
    private IPlateEntityMapper plateEntityMapper;

    @InjectMocks
    private PlateJpaAdapter plateJpaAdapter;

    @Test
    void createPlateSuccessful() {
        Plate plate = new Plate();
        PlateEntity plateEntity = new PlateEntity();

        when(plateEntityMapper.toPlateEntity(plate)).thenReturn(plateEntity);
        plateJpaAdapter.createPlate(plate);

        verify(plateEntityMapper).toPlateEntity(plate);
        verify(plateRepository).save(plateEntity);
    }

    @Test
    void upsertPlateSuccessful() {
        Long idPlate = 12L;
        Plate plate = new Plate();
        plate.setId(idPlate);
        PlateEntity plateEntity = new PlateEntity();
        plateEntity.setId(idPlate);

        when(plateEntityMapper.toPlateEntity(plate)).thenReturn(plateEntity);
        when(plateRepository.findById(anyLong())).thenReturn(Optional.of(plateEntity));
        plateJpaAdapter.upsertPlate(plate);

        verify(plateEntityMapper).toPlateEntity(plate);
        verify(plateRepository).save(plateEntity);
    }

    @Test
    void upsertPlateFail() {
        Long idPlate = 12L;
        Plate plate = new Plate();
        plate.setId(idPlate);

        when(plateRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoDataFoundException.class, ()-> plateJpaAdapter.upsertPlate(plate));

    }

}