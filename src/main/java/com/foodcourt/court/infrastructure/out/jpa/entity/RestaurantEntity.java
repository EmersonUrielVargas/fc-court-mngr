package com.foodcourt.court.infrastructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "Restaurantes")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RestaurantEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(nullable = false)
    private Long id;

    private String nombre;

    private String direccion;

    @Column(name = "id_propietario")
    private Long idPropietario;

    private String telefono;

    @Column(name = "url_logo")
    private String urlLogo;

    private String nit;

}
