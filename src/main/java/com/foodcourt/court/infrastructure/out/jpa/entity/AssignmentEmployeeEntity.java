package com.foodcourt.court.infrastructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "asignacion_empleados")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AssignmentEmployeeEntity {

    @EmbeddedId
    private AssignmentEmployeePK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_restaurante", referencedColumnName = "id", insertable = false, updatable = false)
    private RestaurantEntity restaurante;

}
