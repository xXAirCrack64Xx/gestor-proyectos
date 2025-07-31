package com.davivienda.tusproyectos.tarea.resource;

import com.davivienda.tusproyectos.tarea.repository.TareaRepository;
import com.davivienda.tusproyectos.tarea.resource.dto.TareaDTO;
import com.davivienda.tusproyectos.tarea.entity.Tarea;
import com.davivienda.tusproyectos.tarea.service.TareaService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/proyectos/{proyectoId}/tareas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Tareas", description = "Gestión de tareas asociadas a un proyecto del usuario autenticado")
@Authenticated
public class TareaResource {

    @Inject
    TareaService tareaService;

    @Inject
    TareaRepository tareaRepository;


    @Inject
    JsonWebToken jwt;

    private String getUsername() {
        return jwt.getName(); // el UPN del JWT
    }

    @GET
    @Operation(summary = "Listar tareas", description = "Obtiene todas las tareas de un proyecto del usuario autenticado")
    @RolesAllowed("user")
    public List<Tarea> listar(@PathParam("proyectoId") Long proyectoId) {
        return tareaService.listar(getUsername(), proyectoId);
    }

    @PUT
    @Path("/{id}/toggle")
    @Transactional
    public Response toggleTarea(@PathParam("id") Long id) {
        Tarea tarea = tareaRepository.findById(id);
        if (tarea == null) {
            throw new NotFoundException("Tarea no encontrada con id " + id);
        }
        tarea.setCompletada(!tarea.isCompletada());

        return Response.ok(tarea).build();
    }

    @POST
    @Operation(summary = "Crear tarea", description = "Crea una tarea asociada a un proyecto del usuario autenticado")
    @RolesAllowed("user")
    public Tarea crear(@PathParam("proyectoId") Long proyectoId, TareaDTO dto) {
        return tareaService.crear(getUsername(), proyectoId, dto);
    }

    @PUT
    @Path("/{tareaId}")
    @Operation(summary = "Actualizar tarea", description = "Actualiza una tarea de un proyecto del usuario autenticado")
    @RolesAllowed("user")
    public Tarea actualizar(@PathParam("proyectoId") Long proyectoId, @PathParam("tareaId") Long tareaId, TareaDTO dto) {
        return tareaService.actualizar(getUsername(), proyectoId, tareaId, dto);
    }

    @DELETE
    @Path("/{tareaId}")
    @Operation(summary = "Eliminar tarea", description = "Elimina una tarea de un proyecto del usuario autenticado")
    @RolesAllowed("user")
    public void eliminar(@PathParam("proyectoId") Long proyectoId, @PathParam("tareaId") Long tareaId) {
        tareaService.eliminar(getUsername(), proyectoId, tareaId);
    }
}
