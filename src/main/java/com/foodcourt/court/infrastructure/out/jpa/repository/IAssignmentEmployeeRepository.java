package com.foodcourt.court.infrastructure.out.jpa.repository;

import com.foodcourt.court.infrastructure.out.jpa.entity.AssignmentEmployeeEntity;
import com.foodcourt.court.infrastructure.out.jpa.entity.AssignmentEmployeePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAssignmentEmployeeRepository extends JpaRepository<AssignmentEmployeeEntity, AssignmentEmployeePK> {
}
