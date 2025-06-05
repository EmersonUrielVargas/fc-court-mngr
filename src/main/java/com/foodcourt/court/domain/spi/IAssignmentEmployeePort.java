package com.foodcourt.court.domain.spi;

import com.foodcourt.court.domain.model.AssignmentEmployee;

import java.util.List;
import java.util.Optional;

public interface IAssignmentEmployeePort {
    void createAssignment(AssignmentEmployee assignment);

    Optional<AssignmentEmployee> getAssignment(Long restaurantId, Long employeeId);
    List<Long> getEmployeesIdByRestaurantId(Long restaurantId);

}
