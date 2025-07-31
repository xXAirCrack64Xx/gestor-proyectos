package com.davivienda.tusproyectos.usuario.resource;

import com.davivienda.tusproyectos.usuario.resource.dto.AuthResponse;
import com.davivienda.tusproyectos.usuario.resource.dto.LoginRequest;
import com.davivienda.tusproyectos.usuario.resource.dto.RegisterRequest;
import com.davivienda.tusproyectos.usuario.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UsuarioService usuarioService;

    @POST
    @Path("/register")
    @Operation(summary = "Registro de usuario", description = "Crea un usuario nuevo")
    public String register(@Valid RegisterRequest request) {
        return usuarioService.register(request);
    }

    @POST
    @Path("/login")
    @Operation(summary = "Login de usuario", description = "Autentica un usuario y devuelve un JWT")
    public AuthResponse login(@Valid LoginRequest request) {
        return usuarioService.login(request);
    }

}
