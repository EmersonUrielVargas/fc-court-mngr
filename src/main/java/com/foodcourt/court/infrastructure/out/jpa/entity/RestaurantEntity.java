package com.foodcourt.court.infrastructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "Restaurantes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
