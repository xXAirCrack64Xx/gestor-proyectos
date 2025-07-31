package com.davivienda.tusproyectos.common.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class ProjectNotFoundException extends WebApplicationException {
    private final String errorCode;

    public ProjectNotFoundException(String message) {
        super(message, Response.Status.NOT_FOUND);
        this.errorCode = ErrorCodes.PROJECT_NOT_FOUND.code();
    }

    public String getErrorCode() {
        return errorCode;
    }
}
