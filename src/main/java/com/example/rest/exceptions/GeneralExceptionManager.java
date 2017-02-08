package com.example.rest.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Arrays;

@Provider
public class GeneralExceptionManager implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(Arrays.toString(e.getStackTrace()))
                .type("text/plain")
                .build();
    }
}

