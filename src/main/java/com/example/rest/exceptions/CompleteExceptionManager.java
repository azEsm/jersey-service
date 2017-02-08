package com.example.rest.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.nio.file.Paths;
import java.util.concurrent.CompletionException;

@Provider
public class CompleteExceptionManager implements ExceptionMapper<CompletionException> {

    @Override
    public Response toResponse(CompletionException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(Paths.get("img", "error.jpg").toFile())
                .type("image/jpg")
                .build();
    }
}

