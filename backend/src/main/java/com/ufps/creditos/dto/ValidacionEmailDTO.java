package com.ufps.creditos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidacionEmailDTO {
    private String email;
    private String documento;
}
