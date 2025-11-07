package com.ufps.creditos.service;

import com.ufps.creditos.dto.ValidacionEmailDTO;
import com.ufps.creditos.entity.Validacion;
import com.ufps.creditos.repository.ValidacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.UUID;

@Service
public class ValidacionService {

    @Autowired
    private ValidacionRepository validacionRepository;

    @Autowired
    private JavaMailSender mailSender;

    
    public Validacion crearValidacionEmail(ValidacionEmailDTO dto) {
        
        if (validacionRepository.existsByEmailAndDocumento(dto.getEmail(), dto.getDocumento())) {
            throw new RuntimeException("Ya existe una validación para este email y documento");
        }

        Validacion validacion = new Validacion();
        validacion.setEmail(dto.getEmail());
        validacion.setDocumento(dto.getDocumento());
        validacion.setFecha(LocalDateTime.now());
        validacion.setEstado("Pendiente");
        validacion.setToken(generarToken());
        validacion.setCodigo(generarCodigo());

        Validacion validacionGuardada = validacionRepository.save(validacion);

       
        enviarEmailValidacion(validacion.getEmail(), validacion.getToken(), validacion.getCodigo());

        return validacionGuardada;
    }

 
    public boolean validarToken(String token) {
        Validacion validacion = validacionRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token no encontrado"));

        
        LocalDateTime ahora = LocalDateTime.now();
        long minutosTranscurridos = ChronoUnit.MINUTES.between(validacion.getFecha(), ahora);

        if (minutosTranscurridos > 15) {
            return false; 
        }

        if ("Pendiente".equals(validacion.getEstado())) {
            validacion.setEstado("Validada");
            validacionRepository.save(validacion);
            return true;
        }

        return "Validada".equals(validacion.getEstado()); 
    }

    private String generarToken() {
        return UUID.randomUUID().toString();
    }

    private String generarCodigo() {
        Random random = new Random();
        int codigo = 1000000000 + random.nextInt(900000000);
        return String.valueOf(codigo).substring(0, 10);
    }

    private void enviarEmailValidacion(String destinatario, String token, String codigo) {
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(destinatario);
            mensaje.setSubject("Validación de Email - Sistema de Créditos UFPS");
            mensaje.setText(
                "Hola,\n\n" +
                "Tu token de validación es: " + token + "\n" +
                "Tu código es: " + codigo + "\n\n" +
                "Este token expira en 15 minutos.\n\n" +
                "Sistema de Gestión de Créditos\n" +
                "Universidad Francisco de Paula Santander"
            );
            
            mailSender.send(mensaje);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el email: " + e.getMessage());
        }
    }
}
