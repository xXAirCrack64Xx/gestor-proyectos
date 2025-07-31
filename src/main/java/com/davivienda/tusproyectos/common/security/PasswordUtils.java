package com.davivienda.tusproyectos.common.security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    private PasswordUtils() {}

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean verifyPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
