package com.davivienda.tusproyectos.common.exception;

public enum ErrorCodes {
    USER_NOT_FOUND("USER_NOT_FOUND"),
    INVALID_PASSWORD("INVALID_PASSWORD"),
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS"),
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS"),
    WEAK_PASSWORD("WEAK_PASSWORD"),
    TASK_NOT_FOUND("TASK_NOT_FOUND"),
    PROJECT_NOT_FOUND("PROJECT_NOT_FOUND"),
    INTERNAL_ERROR("INTERNAL_ERROR");

    private final String code;

    ErrorCodes(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}

