package com.foodcourt.court.infrastructure.out.jpa.entity;

import com.foodcourt.court.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "pedidos")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(nullable = false)
    private Long id;

    private LocalDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_restaurante")
    private RestaurantEntity restaurante;

    private Integer codigo;

    private String estado = OrderStatus.PENDING.getMessage();

    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "id_chef")
    private Long idChef;

    @OneToMany(
            mappedBy = "pedido",
            cascade = CascadeType.ALL
    )
    private List<OrderPlateEntity> platos;

}
