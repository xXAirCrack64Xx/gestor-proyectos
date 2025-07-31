package com.davivienda.tusproyectos.usuario.resource.dto;
import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String username;
}