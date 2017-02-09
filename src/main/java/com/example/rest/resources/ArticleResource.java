package com.example.rest.resources;

import com.example.rest.services.CombineArticleData;
import com.example.rest.services.DataService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Root resource (exposed at "article" path)
 */
@Path("article")
public class ArticleResource {

    private final DataService dataService;

    @Inject
    public ArticleResource(DataService dataService) {
        this.dataService = dataService;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public void asyncGet(@Suspended final AsyncResponse asyncResponse) {
        dataService
                .findTopic()
                .thenCombine(dataService.findText(), new CombineArticleData())
                .thenApply(asyncResponse::resume)
                .exceptionally(asyncResponse::resume);

        asyncResponse.setTimeout(1, TimeUnit.SECONDS);
        asyncResponse.setTimeoutHandler(ar -> ar.resume(new TimeoutException()));
    }
}