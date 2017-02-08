package com.example.rest.services;

import javax.annotation.ManagedBean;
import javax.annotation.PreDestroy;
import javax.ws.rs.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@ManagedBean
@Path("data")
public class DataService {
    private final ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);

    private CompletableFuture<String> find(String path) {
        CompletableFuture<String> promise = new CompletableFuture<>();

        CompletableFuture.runAsync(() -> {
            try {
                promise.complete(new String(Files.readAllBytes(Paths.get("data", path))));
            } catch (IOException e) {
                promise.completeExceptionally(e);
            }
        });

        pool.schedule(() -> promise.completeExceptionally(new TimeoutException()),
                1,
                TimeUnit.SECONDS
        );

        return promise;
    }

    public CompletableFuture<String> findTopic() {
        return find("topic");
    }

    public CompletableFuture<String> findText() {
        return find("text");
    }

    @PreDestroy
    public void shutdown() {
        pool.shutdownNow();
    }
}