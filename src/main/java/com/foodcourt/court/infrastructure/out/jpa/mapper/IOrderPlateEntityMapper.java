package com.foodcourt.court.infrastructure.out.jpa.mapper;

import com.foodcourt.court.domain.model.OrderPlate;
import com.foodcourt.court.infrastructure.out.jpa.entity.OrderPlateEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderPlateEntityMapper {

    @Mapping(source = "quantity", target = "cantidad")
    @Mapping(source = "orderId", target = "id.idPedido")
    @Mapping(source = "plateId", target = "id.idPlato")
    OrderPlateEntity toOrderPlateEntity(OrderPlate orderPlate);

    @InheritInverseConfiguration
    @Mapping(source = "plato.id", target = "plateId")
    @Mapping(source = "pedido.id", target = "orderId")
    OrderPlate toOrderPlate(OrderPlateEntity orderPlateEntity);

}
