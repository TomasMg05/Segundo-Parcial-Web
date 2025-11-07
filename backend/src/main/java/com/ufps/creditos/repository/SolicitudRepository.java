package com.ufps.creditos.repository;

import com.ufps.creditos.entity.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Integer> {
    Optional<Solicitud> findByCodigoRadicado(String codigoRadicado);
    
    @Query("SELECT COUNT(s) > 0 FROM Solicitud s WHERE s.codeudor.id = :codeudorId AND s.estado.descripcion = :estado")
    boolean existsByCodeudorIdAndEstadoDescripcion(@Param("codeudorId") Integer codeudorId, @Param("estado") String estado);
    
    @Query("SELECT COUNT(s) > 0 FROM Solicitud s WHERE s.solicitante.id = :solicitanteId AND s.estado.descripcion IN :estados")
    boolean existsBySolicitanteIdAndEstadoDescripcionIn(@Param("solicitanteId") Integer solicitanteId, @Param("estados") java.util.List<String> estados);
}
