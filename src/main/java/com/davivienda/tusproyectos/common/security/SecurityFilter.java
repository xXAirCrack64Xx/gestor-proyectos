package com.davivienda.tusproyectos.common.security;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.security.Principal;

/**
 * Filtro de seguridad que protege todas las rutas excepto las públicas.
 * <p>
 * Verifica la presencia y validez de un token JWT.
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {

    private static final String[] PUBLIC_PATHS = {"/auth/login", "/auth/register"};

    @Inject
    JWTService jwtService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();

        // Permitir rutas públicas
        for (String publicPath : PUBLIC_PATHS) {
            if (path.startsWith(publicPath)) {
                return;
            }
        }

        // Validar cabecera Authorization
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            abortRequest(requestContext, "Token requerido");
            return;
        }

        String token = authHeader.substring("Bearer ".length());

        try {
            String username = jwtService.validateToken(token);

            // Inyectar usuario en SecurityContext
            final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
            requestContext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return () -> username;
                }

                @Override
                public boolean isUserInRole(String role) {
                    return true; // puedes adaptar roles aquí
                }

                @Override
                public boolean isSecure() {
                    return currentSecurityContext.isSecure();
                }

                @Override
                public String getAuthenticationScheme() {
                    return "Bearer";
                }
            });
        } catch (Exception e) {
            abortRequest(requestContext, "Token inválido o expirado");
        }
    }

    private void abortRequest(ContainerRequestContext requestContext, String message) {
        requestContext.abortWith(
                jakarta.ws.rs.core.Response.status(401)
                        .entity(message)
                        .build()
        );
    }
}


