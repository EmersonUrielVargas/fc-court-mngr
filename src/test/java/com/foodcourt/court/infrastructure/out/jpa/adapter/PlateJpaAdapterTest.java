package com.foodcourt.court.infrastructure.out.jpa.adapter;

import com.foodcourt.court.domain.DataDomainFactory;
import com.foodcourt.court.domain.model.Plate;
import com.foodcourt.court.infrastructure.out.jpa.entity.PlateEntity;
import com.foodcourt.court.infrastructure.out.jpa.mapper.IPlateEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.repository.IPlateRepository;
import com.foodcourt.court.shared.DataConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        Plate plate = DataDomainFactory.createPlate();
        PlateEntity plateEntity = new PlateEntity();

        when(plateEntityMapper.toPlateEntity(plate)).thenReturn(plateEntity);
        plateJpaAdapter.upsertPlate(plate);

        verify(plateEntityMapper).toPlateEntity(plate);
        verify(plateRepository).save(plateEntity);
    }

    @Test
    void upsertPlateSuccessful() {
        Long idPlate = DataConstants.DEFAULT_PLATE_ID;
        Plate plate = DataDomainFactory.createPlate();
        PlateEntity plateEntity = new PlateEntity();
        plateEntity.setId(idPlate);

        when(plateEntityMapper.toPlateEntity(plate)).thenReturn(plateEntity);
        plateJpaAdapter.upsertPlate(plate);

        verify(plateEntityMapper).toPlateEntity(plate);
        verify(plateRepository).save(plateEntity);
    }

}