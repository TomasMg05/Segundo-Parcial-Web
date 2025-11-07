package com.ufps.creditos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "persona")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "documento", length = 10, nullable = false, unique = true)
    private String documento;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "telefono", length = 10)
    private String telefono;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;
}
