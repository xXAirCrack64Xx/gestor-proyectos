package com.davivienda.tusproyectos.common.security;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.Duration;
import java.util.Set;

/**
 * Servicio encargado de la generación y validación de tokens JWT
 * para la autenticación y autorización de usuarios en la aplicación.
 * <p>
 * Utiliza la librería SmallRye JWT integrada en Quarkus.
 */
@ApplicationScoped
public class JWTService {

    @Inject
    JWTParser jwtParser;

    /**
     * Genera un token JWT firmado que expira en 2 horas.
     * <p>
     * El token contiene:
     * <ul>
     *     <li><b>issuer</b>: "https://davivienda.com/tusproyectos"</li>
     *     <li><b>UPN</b>: el username del usuario autenticado</li>
     *     <li><b>groups</b>: conjunto de roles (por defecto: "user")</li>
     *     <li><b>exp</b>: tiempo de expiración (2 horas)</li>
     * </ul>
     *
     * @param username Nombre de usuario para incluir como UPN en el token
     * @return Token JWT firmado y serializado en formato String
     */
    public String generateToken(String username) {
        return Jwt.issuer("https://davivienda.com/tusproyectos")
                .upn(username)
                .groups(Set.of("user"))
                .expiresIn(Duration.ofHours(2))
                .sign();
    }

    /**
     * Valida un token JWT, verifica su firma y tiempo de expiración.
     * <p>
     * Si el token es válido, retorna el nombre de usuario (UPN) contenido en él.
     * Si el token es inválido, mal formado o expiró, se lanza una excepción.
     *
     * @param token Token JWT a validar (sin el prefijo "Bearer ")
     * @return Nombre de usuario (UPN) contenido en el token
     * @throws RuntimeException si el token no es válido o ya expiró
     */
    public String validateToken(String token) {
        try {
            var jsonWebToken = jwtParser.parse(token);
            return jsonWebToken.getName(); // Por convención retorna el UPN
        } catch (ParseException e) {
            throw new RuntimeException("Token inválido o expirado", e);
        }
    }
}

