package com.davivienda.tusproyectos.tarea.repository;

import com.davivienda.tusproyectos.tarea.entity.Tarea;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class TareaRepository implements PanacheRepository<Tarea> {
    public List<Tarea> findByProyectoId(Long proyectoId) {
        return list("proyecto.id", proyectoId);
    }
}
