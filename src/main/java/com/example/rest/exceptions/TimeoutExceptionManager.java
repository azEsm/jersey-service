package com.example.rest.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.concurrent.TimeoutException;

@Provider
public class TimeoutExceptionManager implements ExceptionMapper<TimeoutException> {

    @Override
    public Response toResponse(TimeoutException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("TIME IS UP!!!")
                .type("text/plain")
                .build();
    }
}

