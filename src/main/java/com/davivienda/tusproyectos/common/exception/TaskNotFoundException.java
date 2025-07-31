package com.davivienda.tusproyectos.common.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class TaskNotFoundException extends WebApplicationException {
    private final String errorCode;

    public TaskNotFoundException(String message) {
        super(message, Response.Status.NOT_FOUND);
        this.errorCode = ErrorCodes.TASK_NOT_FOUND.code();
    }

    public String getErrorCode() {
        return errorCode;
    }
}

