package com.davivienda.tusproyectos.common.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        log.error("Exception capturada: ", exception);

        if (exception instanceof ProjectNotFoundException e) {
            return buildResponse(e.getMessage(), e.getErrorCode(), Response.Status.NOT_FOUND);
        }

        if (exception instanceof TaskNotFoundException e) {
            return buildResponse(e.getMessage(), e.getErrorCode(), Response.Status.NOT_FOUND);
        }

        if (exception instanceof BadRequestException e) {
            return buildResponse(e.getMessage(), e.getErrorCode(), Response.Status.BAD_REQUEST);
        }

        return buildResponse("Error interno del servidor",
                ErrorCodes.INTERNAL_ERROR.code(),
                Response.Status.INTERNAL_SERVER_ERROR);
    }

    private Response buildResponse(String message, String errorCode, Response.Status status) {
        ErrorResponse errorResponse = new ErrorResponse(message, errorCode, status.getStatusCode());
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(errorResponse)
                .build();
    }
}

