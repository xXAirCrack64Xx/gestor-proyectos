package com.davivienda.tusproyectos.tarea.service;

import com.davivienda.tusproyectos.common.exception.BadRequestException;
import com.davivienda.tusproyectos.common.exception.ErrorCodes;
import com.davivienda.tusproyectos.proyecto.entity.Proyecto;
import com.davivienda.tusproyectos.proyecto.repository.ProyectoRepository;
import com.davivienda.tusproyectos.tarea.entity.Tarea;
import com.davivienda.tusproyectos.tarea.repository.TareaRepository;
import com.davivienda.tusproyectos.tarea.resource.dto.TareaDTO;
import com.davivienda.tusproyectos.usuario.entity.Usuario;
import com.davivienda.tusproyectos.usuario.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class TareaService {

    private final TareaRepository tareaRepository;
    private final ProyectoRepository proyectoRepository;
    private final UsuarioRepository usuarioRepository;

    private Usuario obtenerUsuario(String username) {
        return usuarioRepository.findByUsername(username.toLowerCase())
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado", ErrorCodes.USER_NOT_FOUND.code()));
    }

    private Proyecto obtenerProyecto(Long proyectoId, Usuario usuario) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId);
        if (proyecto == null || !proyecto.getUsuario().getId().equals(usuario.getId())) {
            throw new BadRequestException("Proyecto no encontrado o no autorizado", ErrorCodes.PROJECT_NOT_FOUND.code());
        }
        return proyecto;
    }

    public List<Tarea> listar(String username, Long proyectoId) {
        Usuario usuario = obtenerUsuario(username);
        Proyecto proyecto = obtenerProyecto(proyectoId, usuario);
        return tareaRepository.findByProyectoId(proyecto.getId());
    }

    @Transactional
    public Tarea crear(String username, Long proyectoId, TareaDTO dto) {
        Usuario usuario = obtenerUsuario(username);
        Proyecto proyecto = obtenerProyecto(proyectoId, usuario);

        Tarea tarea = Tarea.builder()
                .titulo(dto.getTitulo())
                .descripcion(dto.getDescripcion())
                .completada(dto.isCompletada())
                .proyecto(proyecto)
                .build();

        tareaRepository.persist(tarea);
        return tarea;
    }

    @Transactional
    public Tarea actualizar(String username, Long proyectoId, Long tareaId, TareaDTO dto) {
        Usuario usuario = obtenerUsuario(username);
        Proyecto proyecto = obtenerProyecto(proyectoId, usuario);

        Tarea tarea = tareaRepository.findById(tareaId);
        if (tarea == null || !tarea.getProyecto().getId().equals(proyecto.getId())) {
            throw new BadRequestException("Tarea no encontrada o no autorizada", ErrorCodes.TASK_NOT_FOUND.code());
        }

        tarea.setTitulo(dto.getTitulo());
        tarea.setDescripcion(dto.getDescripcion());
        tarea.setCompletada(dto.isCompletada());
        return tarea;
    }

    @Transactional
    public void eliminar(String username, Long proyectoId, Long tareaId) {
        Usuario usuario = obtenerUsuario(username);
        Proyecto proyecto = obtenerProyecto(proyectoId, usuario);

        Tarea tarea = tareaRepository.findById(tareaId);
        if (tarea == null || !tarea.getProyecto().getId().equals(proyecto.getId())) {
            throw new BadRequestException("Tarea no encontrada o no autorizada", ErrorCodes.TASK_NOT_FOUND.code());
        }

        tareaRepository.delete(tarea);
    }
}
