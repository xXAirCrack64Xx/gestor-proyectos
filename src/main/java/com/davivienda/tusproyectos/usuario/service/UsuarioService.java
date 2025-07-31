package com.davivienda.tusproyectos.usuario.service;

import com.davivienda.tusproyectos.common.exception.BadRequestException;
import com.davivienda.tusproyectos.common.exception.ErrorCodes;
import com.davivienda.tusproyectos.common.security.JWTService;
import com.davivienda.tusproyectos.common.security.PasswordUtils;
import com.davivienda.tusproyectos.common.security.PasswordValidator;
import com.davivienda.tusproyectos.usuario.entity.Usuario;
import com.davivienda.tusproyectos.usuario.repository.UsuarioRepository;
import com.davivienda.tusproyectos.usuario.resource.dto.AuthResponse;
import com.davivienda.tusproyectos.usuario.resource.dto.LoginRequest;
import com.davivienda.tusproyectos.usuario.resource.dto.RegisterRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    JWTService jwtService;

    @Transactional
    public String register(RegisterRequest request) {
        // Validar username duplicado
        if (usuarioRepository.findByUsername(request.getUsername()).isPresent())
            throw new BadRequestException("El nombre de usuario ya existe", ErrorCodes.USER_ALREADY_EXISTS.code());

        // Validar correo duplicado
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent())
            throw new BadRequestException("El correo electrónico ya está registrado", ErrorCodes.EMAIL_ALREADY_EXISTS.code());

        // Validar contraseña
        if (!PasswordValidator.isValid(request.getPassword()))
            throw new BadRequestException(
                    "La contraseña no cumple con los requisitos: mínimo 7 caracteres, un número y un carácter especial",
                    ErrorCodes.WEAK_PASSWORD.code()
            );

        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(PasswordUtils.hashPassword(request.getPassword()))
                .build();
        usuarioRepository.persist(usuario);

        return "Usuario registrado exitosamente";
    }

    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException(
                        "El usuario no existe",
                        ErrorCodes.USER_NOT_FOUND.code()
                ));

        if (!PasswordUtils.verifyPassword(request.getPassword(), usuario.getPasswordHash())) {
            throw new BadRequestException(
                    "Contraseña incorrecta",
                    ErrorCodes.INVALID_PASSWORD.code()
            );
        }

        return createAuthResponse(usuario.getUsername());
    }



    private AuthResponse createAuthResponse(String username) {
        AuthResponse response = new AuthResponse();
        response.setUsername(username);
        response.setToken(jwtService.generateToken(username));
        return response;
    }
}
