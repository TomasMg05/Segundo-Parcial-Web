package com.ufps.creditos.controller;

import com.ufps.creditos.dto.RegistroSolicitudDTO;
import com.ufps.creditos.dto.ResponseDTO;
import com.ufps.creditos.dto.SolicitudDTO;
import com.ufps.creditos.entity.Solicitud;
import com.ufps.creditos.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin(origins = "*")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;
    
    @GetMapping
    public ResponseEntity<List<SolicitudDTO>> listarSolicitudes() {
        try {
            List<SolicitudDTO> solicitudes = solicitudService.listarSolicitudes();
            return ResponseEntity.ok(solicitudes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> registrarSolicitud(@RequestBody RegistroSolicitudDTO dto) {
        try {
            Solicitud solicitud = solicitudService.registrarSolicitud(dto);
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", solicitud.getId());
            response.put("fecha", solicitud.getFecha().toString());
            response.put("codigo_radicado", solicitud.getCodigoRadicado());
            response.put("mensaje", "Solicitud registrada exitosamente");
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/validar")
    public ResponseEntity<?> validarSolicitud(@RequestBody Map<String, String> request) {
        try {
            String token = request.get("token");
            String codigoRadicado = request.get("codigo_radicado");
            
            Solicitud solicitud = solicitudService.validarSolicitudPorToken(token, codigoRadicado);
            
            Map<String, Object> response = new HashMap<>();
            response.put("id", solicitud.getId());
            response.put("fecha", solicitud.getFecha().toString());
            response.put("codigo_radicado", solicitud.getCodigoRadicado());
            response.put("estado", solicitud.getEstado().getDescripcion());
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al validar la solicitud");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
