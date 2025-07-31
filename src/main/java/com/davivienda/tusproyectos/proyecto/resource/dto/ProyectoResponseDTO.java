package com.davivienda.tusproyectos.proyecto.resource.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProyectoResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
}
