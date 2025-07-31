package com.davivienda.tusproyectos.proyecto.resource;

import com.davivienda.tusproyectos.proyecto.resource.dto.ProyectoDTO;
import com.davivienda.tusproyectos.proyecto.entity.Proyecto;
import com.davivienda.tusproyectos.proyecto.resource.dto.ProyectoResponseDTO;
import com.davivienda.tusproyectos.proyecto.service.ProyectoService;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/proyectos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Proyectos", description = "Gestión de proyectos del usuario autenticado")
@Authenticated
public class ProyectoResource {

    @Inject
    ProyectoService proyectoService;

    @Inject
    JsonWebToken jwt;

    private String getUsername() {
        return jwt.getName(); // toma el UPN del JWT
    }

    @GET
    @Path("/{id}")
    public Response obtener(@PathParam("id") Long id) {
        Proyecto proyecto = proyectoService.findById(id);
        if (proyecto == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Proyecto no encontrado\"}")
                    .build();
        }
        return Response.ok(proyecto).build();
    }


    @GET
    @Operation(summary = "Listar proyectos", description = "Obtiene todos los proyectos del usuario autenticado")
    @RolesAllowed("user")
    public List<Proyecto> listar() {
        return proyectoService.listar(getUsername());
    }

    @POST
    @Operation(summary = "Crear proyecto", description = "Crea un proyecto para el usuario autenticado")
    @RolesAllowed("user")
    public Proyecto crear(ProyectoDTO dto) {
        return proyectoService.crear(getUsername(), dto);
    }

    @PUT
    @Path("/{id}")
    public ProyectoResponseDTO actualizar(@PathParam("id") Long id, ProyectoDTO dto) {
        Proyecto actualizado = proyectoService.actualizar(getUsername(), id, dto);
        return proyectoService.toDto(actualizado);
    }


    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar proyecto", description = "Elimina un proyecto existente")
    @RolesAllowed("user")
    public void eliminar(@PathParam("id") Long id) {
        proyectoService.eliminar(getUsername(), id);
    }
}
