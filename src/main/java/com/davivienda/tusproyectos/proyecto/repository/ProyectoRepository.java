package com.davivienda.tusproyectos.proyecto.repository;

import com.davivienda.tusproyectos.proyecto.entity.Proyecto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ProyectoRepository implements PanacheRepository<Proyecto> {
    public List<Proyecto> findByUsuarioId(Long usuarioId) {
        return list("usuario.id", usuarioId);
    }
}
