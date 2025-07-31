package com.davivienda.tusproyectos.tarea.entity;

import com.davivienda.tusproyectos.proyecto.entity.Proyecto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tareas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descripcion;

    private boolean completada;

    @ManyToOne
    @JoinColumn(name = "proyecto_id")
    @JsonBackReference
    private Proyecto proyecto;
}
