package com.ufps.creditos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudDTO {
    private Integer id;
    private String fecha;
    private String solicitante;
    private String codeudor;
    private String estado;
    private String codigo_radicado;
}
