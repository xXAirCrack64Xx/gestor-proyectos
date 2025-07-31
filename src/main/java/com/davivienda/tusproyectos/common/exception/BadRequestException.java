package com.davivienda.tusproyectos.common.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class BadRequestException extends WebApplicationException {
    private final String errorCode;

    public BadRequestException(String message, String errorCode) {
        super(message, Response.Status.BAD_REQUEST);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}

