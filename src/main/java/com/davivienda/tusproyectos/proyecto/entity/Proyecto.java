package com.davivienda.tusproyectos.proyecto.entity;

import com.davivienda.tusproyectos.tarea.entity.Tarea;
import com.davivienda.tusproyectos.usuario.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "proyectos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Tarea> tareas;
}
