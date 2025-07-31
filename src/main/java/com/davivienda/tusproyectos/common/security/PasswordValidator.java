package com.davivienda.tusproyectos.common.security;

import java.util.regex.Pattern;

public class PasswordValidator {

    // Expresión regular:
    // - Mínimo 7 caracteres
    // - Al menos un número
    // - Al menos un carácter especial
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{7,}$");

    private PasswordValidator() {}

    public static boolean isValid(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }
}
