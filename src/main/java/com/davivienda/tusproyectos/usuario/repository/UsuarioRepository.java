package com.davivienda.tusproyectos.usuario.repository;

import com.davivienda.tusproyectos.usuario.entity.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public Optional<Usuario> findByUsername(String username) {
        return find("username", username).firstResultOptional();
    }

    public Optional<Usuario> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}
