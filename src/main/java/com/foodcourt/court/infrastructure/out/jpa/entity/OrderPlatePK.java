package com.foodcourt.court.infrastructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@Setter
@Embeddable
public class OrderPlatePK implements Serializable {

    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "id_plato")
    private Long idPlato;

}
