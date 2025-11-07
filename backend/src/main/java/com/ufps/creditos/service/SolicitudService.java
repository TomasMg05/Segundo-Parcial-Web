package com.ufps.creditos.service;

import com.ufps.creditos.dto.RegistroSolicitudDTO;
import com.ufps.creditos.dto.SolicitudDTO;
import com.ufps.creditos.entity.Estado;
import com.ufps.creditos.entity.Persona;
import com.ufps.creditos.entity.Solicitud;
import com.ufps.creditos.repository.EstadoRepository;
import com.ufps.creditos.repository.PersonaRepository;
import com.ufps.creditos.repository.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public List<SolicitudDTO> listarSolicitudes() {
        List<Solicitud> solicitudes = solicitudRepository.findAll();
        
        return solicitudes.stream()
                .map(s -> new SolicitudDTO(
                        s.getId(),
                        s.getFecha().toString(),
                        s.getSolicitante().getNombre(),
                        s.getCodeudor().getNombre(),
                        s.getEstado().getDescripcion(),
                        s.getCodigoRadicado()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public Solicitud registrarSolicitud(RegistroSolicitudDTO dto) {
        if (dto.getDocumento().equals(dto.getCodeudor().getDocumento())) {
            throw new RuntimeException("El solicitante y el codeudor deben ser personas distintas");
        }

        
        if (dto.getEmail().equals(dto.getCodeudor().getEmail())) {
            throw new RuntimeException("El correo del solicitante y codeudor deben ser diferentes");
        }
        
        
        if (dto.getTelefono() != null && dto.getTelefono().equals(dto.getCodeudor().getTelefono())) {
            throw new RuntimeException("El teléfono del solicitante y codeudor deben ser diferentes");
        }

        
        Persona solicitante = personaRepository.findByDocumento(dto.getDocumento())
                .orElseGet(() -> {
                    Persona p = new Persona();
                    p.setDocumento(dto.getDocumento());
                    p.setNombre(dto.getNombre());
                    p.setEmail(dto.getEmail());
                    p.setTelefono(dto.getTelefono());
                    p.setFechaNacimiento(LocalDate.parse(dto.getFecha_nacimiento()));
                    return personaRepository.save(p);
                });

        
        if (solicitudRepository.existsBySolicitanteIdAndEstadoDescripcionIn(
                solicitante.getId(), Arrays.asList("Aprobada", "Solicitud"))) {
            throw new RuntimeException("El solicitante ya tiene una solicitud en estado Aprobada o Solicitud");
        }

        
        Persona codeudor = personaRepository.findByDocumento(dto.getCodeudor().getDocumento())
                .orElseGet(() -> {
                    Persona p = new Persona();
                    p.setDocumento(dto.getCodeudor().getDocumento());
                    p.setNombre(dto.getCodeudor().getNombre());
                    p.setEmail(dto.getCodeudor().getEmail());
                    p.setTelefono(dto.getCodeudor().getTelefono());
                    p.setFechaNacimiento(LocalDate.parse(dto.getCodeudor().getFecha_nacimiento()));
                    return personaRepository.save(p);
                });

        
        if (solicitudRepository.existsByCodeudorIdAndEstadoDescripcion(codeudor.getId(), "Rechazada")) {
            throw new RuntimeException("El codeudor tiene una solicitud en estado Rechazada");
        }

        
        Solicitud solicitud = new Solicitud();
        solicitud.setFecha(LocalDate.now());
        solicitud.setSolicitante(solicitante);
        solicitud.setCodeudor(codeudor);
        solicitud.setValor(BigDecimal.ZERO);
        
        Estado estadoSolicitud = estadoRepository.findByDescripcion("Solicitud")
                .orElseThrow(() -> new RuntimeException("Estado 'Solicitud' no encontrado"));
        solicitud.setEstado(estadoSolicitud);
        
        solicitud.setObservacion(dto.getObservacion());
        solicitud.setCodigoRadicado(generarCodigoRadicado());

        return solicitudRepository.save(solicitud);
    }

    public Solicitud validarSolicitudPorToken(String token, String codigoRadicado) {
        Solicitud solicitud = solicitudRepository.findByCodigoRadicado(codigoRadicado)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        
        return solicitud;
    }

    private String generarCodigoRadicado() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder codigo = new StringBuilder();
        
        for (int i = 0; i < 10; i++) {
            codigo.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }
        
        
        if (solicitudRepository.findByCodigoRadicado(codigo.toString()).isPresent()) {
            return generarCodigoRadicado(); // Recursivo si ya existe
        }
        
        return codigo.toString();
    }
}
