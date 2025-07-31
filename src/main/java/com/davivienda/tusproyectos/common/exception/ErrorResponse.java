package com.davivienda.tusproyectos.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message; // Mensaje de error legible
    private String code;    // Código de error único
    private int status;     // Código HTTP
}
