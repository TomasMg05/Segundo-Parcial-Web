package com.ufps.creditos.repository;

import com.ufps.creditos.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
    Optional<Estado> findByDescripcion(String descripcion);
}
