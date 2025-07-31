package com.davivienda.tusproyectos.proyecto.service;

import com.davivienda.tusproyectos.common.exception.BadRequestException;
import com.davivienda.tusproyectos.common.exception.ErrorCodes;
import com.davivienda.tusproyectos.proyecto.resource.dto.ProyectoDTO;
import com.davivienda.tusproyectos.proyecto.entity.Proyecto;
import com.davivienda.tusproyectos.proyecto.repository.ProyectoRepository;
import com.davivienda.tusproyectos.proyecto.resource.dto.ProyectoResponseDTO;
import com.davivienda.tusproyectos.usuario.entity.Usuario;
import com.davivienda.tusproyectos.usuario.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Obtiene el usuario por su username.
     */
    private Usuario obtenerUsuario(String username) {
        return usuarioRepository.findByUsername(username.toLowerCase())
                .orElseThrow(() -> new BadRequestException(
                        "Usuario no encontrado",
                        ErrorCodes.USER_NOT_FOUND.code()
                ));
    }

    public List<Proyecto> listar(String username) {
        Usuario usuario = obtenerUsuario(username);
        return proyectoRepository.findByUsuarioId(usuario.getId());
    }

    public Proyecto findById(Long id) {
        return proyectoRepository.findById(id);
    }

    @Transactional
    public Proyecto crear(String username, ProyectoDTO dto) {
        Usuario usuario = obtenerUsuario(username);

        Proyecto proyecto = Proyecto.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .usuario(usuario)
                .build();

        proyectoRepository.persist(proyecto);
        return proyecto;
    }

    @Transactional
    public Proyecto actualizar(String username, Long proyectoId, ProyectoDTO dto) {
        Usuario usuario = obtenerUsuario(username);

        Proyecto proyecto = proyectoRepository.findById(proyectoId);
        if (proyecto == null || !proyecto.getUsuario().getId().equals(usuario.getId())) {
            throw new BadRequestException("Proyecto no encontrado o no autorizado", ErrorCodes.PROJECT_NOT_FOUND.code());
        }

        proyecto.setNombre(dto.getNombre());
        proyecto.setDescripcion(dto.getDescripcion());
        return proyecto;
    }

    @Transactional
    public void eliminar(String username, Long proyectoId) {
        Usuario usuario = obtenerUsuario(username);

        Proyecto proyecto = proyectoRepository.findById(proyectoId);
        if (proyecto == null || !proyecto.getUsuario().getId().equals(usuario.getId())) {
            throw new BadRequestException("Proyecto no encontrado o no autorizado", ErrorCodes.PROJECT_NOT_FOUND.code());
        }

        proyectoRepository.delete(proyecto);
    }

    public ProyectoResponseDTO toDto(Proyecto proyecto) {
        return ProyectoResponseDTO.builder()
                .id(proyecto.getId())
                .nombre(proyecto.getNombre())
                .descripcion(proyecto.getDescripcion())
                .build();
    }

}


