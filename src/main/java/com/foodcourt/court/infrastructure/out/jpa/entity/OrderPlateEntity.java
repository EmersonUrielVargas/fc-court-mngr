package com.foodcourt.court.infrastructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "pedidos_platos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderPlateEntity {
    @EmbeddedId
    private OrderPlatePK id;

    @ManyToOne
    @MapsId("idPedido")
    @JoinColumn(name = "id_pedido", insertable = false, updatable = false)
    private OrderEntity pedido;

    @ManyToOne
    @JoinColumn(name = "id_plato", insertable = false, updatable = false)
    private PlateEntity plato;

    private Integer cantidad;

}
