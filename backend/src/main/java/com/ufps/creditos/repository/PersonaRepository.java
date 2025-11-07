package com.ufps.creditos.repository;

import com.ufps.creditos.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    Optional<Persona> findByDocumento(String documento);
    boolean existsByDocumento(String documento);
}
