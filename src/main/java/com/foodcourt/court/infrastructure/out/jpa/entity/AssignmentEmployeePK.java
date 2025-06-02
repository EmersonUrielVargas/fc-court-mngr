package com.foodcourt.court.infrastructure.out.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AssignmentEmployeePK implements Serializable {

    @Column(name = "id_restaurante")
    private Long restauranteId;

    @Column(name = "id_empleado")
    private Long empleadoId;

}
