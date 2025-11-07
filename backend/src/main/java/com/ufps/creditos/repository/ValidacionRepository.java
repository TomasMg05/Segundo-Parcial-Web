package com.ufps.creditos.repository;

import com.ufps.creditos.entity.Validacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValidacionRepository extends JpaRepository<Validacion, Integer> {
    Optional<Validacion> findByToken(String token);
    Optional<Validacion> findByCodigo(String codigo);
    boolean existsByEmailAndDocumento(String email, String documento);
}
