package com.ufps.creditos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroSolicitudDTO {
    private String telefono;
    private String documento;
    private String nombre;
    private String email;
    private String fecha_nacimiento;
    private CodeudorDTO codeudor;
    private String observacion;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CodeudorDTO {
        private String documento;
        private String nombre;
        private String email;
        private String fecha_nacimiento;
        private String telefono;
    }
}
