package com.foodcourt.court.infrastructure.out.jpa.mapper;

import com.foodcourt.court.domain.model.AssignmentEmployee;
import com.foodcourt.court.infrastructure.out.jpa.entity.AssignmentEmployeeEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IAssignmentEmployeeEntityMapper {

    @Mapping(source = "restaurantId", target = "id.restauranteId")
    @Mapping(source = "employeeId", target = "id.empleadoId")
    AssignmentEmployeeEntity toAssignmentEmployeeEntity(AssignmentEmployee assignmentEmployee);

    @InheritInverseConfiguration
    AssignmentEmployee toAssignmentEmployee(AssignmentEmployeeEntity assignmentEmployeeEntity);

}
