package com.ufps.creditos.controller;

import com.ufps.creditos.dto.ValidacionEmailDTO;
import com.ufps.creditos.entity.Validacion;
import com.ufps.creditos.service.ValidacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/validacion")
@CrossOrigin(origins = "*")
public class ValidacionController {

    @Autowired
    private ValidacionService validacionService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearValidacion(@RequestBody ValidacionEmailDTO dto) {
        try {
            Validacion validacion = validacionService.crearValidacionEmail(dto);
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", validacion.getToken());
            response.put("codigo", validacion.getCodigo());
            response.put("mensaje", "Validación creada. Se ha enviado un email con el token.");
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear la validación");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/validar")
    public ResponseEntity<?> validarToken(@RequestBody Map<String, String> request) {
        try {
            String token = request.get("token");
            boolean valido = validacionService.validarToken(token);
            
            Map<String, Object> response = new HashMap<>();
            response.put("valido", valido);
            response.put("mensaje", valido ? "Token validado correctamente" : "Token expirado o ya validado");
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("valido", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("valido", false);
            response.put("error", "Error al validar el token");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
