package com.example.rest;

import com.example.rest.exceptions.CustomCompleteExceptionManager;
import com.example.rest.resources.ArticleResource;
import com.example.rest.services.DataService;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Main class.
 *
 */
public class Main {
    private static final String PORT = System.getProperty("port", "8080");

    // Base URI the Grizzly HTTP server will listen on
    static final String BASE_URI = "http://localhost:" + PORT + "/cf/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        final ResourceConfig rc = new ResourceConfig().packages(
                ArticleResource.class.getPackage().getName(),
                DataService.class.getPackage().getName(),
                CustomCompleteExceptionManager.class.getPackage().getName()
        );

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.shutdown();
    }
}

