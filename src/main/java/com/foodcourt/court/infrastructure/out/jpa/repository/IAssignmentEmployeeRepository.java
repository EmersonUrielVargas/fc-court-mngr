package com.foodcourt.court.infrastructure.out.jpa.repository;

import com.foodcourt.court.infrastructure.out.jpa.entity.AssignmentEmployeeEntity;
import com.foodcourt.court.infrastructure.out.jpa.entity.AssignmentEmployeePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAssignmentEmployeeRepository extends JpaRepository<AssignmentEmployeeEntity, AssignmentEmployeePK> {

    @Query(
            " SELECT ae.id.empleadoId " +
            " FROM AssignmentEmployeeEntity ae" +
            " WHERE ae.id.restauranteId = :restaurantId")
    List<Long> findIdsByRestaurantId(Long restaurantId);
}
