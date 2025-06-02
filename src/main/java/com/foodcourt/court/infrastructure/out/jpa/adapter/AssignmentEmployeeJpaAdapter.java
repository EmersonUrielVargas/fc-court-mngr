package com.foodcourt.court.infrastructure.out.jpa.adapter;

import com.foodcourt.court.domain.model.AssignmentEmployee;
import com.foodcourt.court.domain.spi.IAssignmentEmployeePort;
import com.foodcourt.court.infrastructure.out.jpa.entity.AssignmentEmployeePK;
import com.foodcourt.court.infrastructure.out.jpa.mapper.IAssignmentEmployeeEntityMapper;
import com.foodcourt.court.infrastructure.out.jpa.repository.IAssignmentEmployeeRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class AssignmentEmployeeJpaAdapter implements IAssignmentEmployeePort {

    private final IAssignmentEmployeeRepository assignmentEmployeeRepository;
    private final IAssignmentEmployeeEntityMapper assignmentEmployeeEntityMapper;


    @Override
    public void createAssignment(AssignmentEmployee assignment) {
        assignmentEmployeeRepository.save(assignmentEmployeeEntityMapper.toAssignmentEmployeeEntity(assignment));
    }

    @Override
    public Optional<AssignmentEmployee> getAssignment(Long restaurantId, Long employeeId) {
        AssignmentEmployeePK assignmentId = new AssignmentEmployeePK(restaurantId, employeeId);
        return assignmentEmployeeRepository.findById(assignmentId).map(assignmentEmployeeEntityMapper::toAssignmentEmployee);
    }
}
