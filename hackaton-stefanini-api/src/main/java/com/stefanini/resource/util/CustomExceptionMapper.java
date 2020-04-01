package com.stefanini.resource.util;

import com.stefanini.dto.ErroDto;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

@Provider
public class CustomExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final String ERRO_DE_VALIDACAO = "Erro de validação";

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(prepareErrors(exception))
                .build();
    }

    private List<ErroDto> prepareErrors(ConstraintViolationException exception) {
        return exception.getConstraintViolations().stream()
                .map(this::error)
                .collect(Collectors.toList());
    }

    private ErroDto error(ConstraintViolation<?> cv) {
        return new ErroDto(getProperty(cv), cv.getMessage(), cv.getInvalidValue());
    }

    private String getProperty(ConstraintViolation<?> cv) {
        return cv.getPropertyPath()
                .toString()
                .replaceAll("([a-zA-z-09]+\\.)([a-zA-z-09]+\\.)", "");
    }

}
