package com.ufps.creditos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "validacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Validacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "documento", length = 10, nullable = false)
    private String documento;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "estado", length = 20, nullable = false)
    private String estado; // "Pendiente" o "Validada"

    @Column(name = "token", length = 100, nullable = false, unique = true)
    private String token;

    @Column(name = "codigo", length = 10, nullable = false, unique = true)
    private String codigo;
}
