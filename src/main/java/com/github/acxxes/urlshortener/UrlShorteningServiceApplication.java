package com.github.acxxes.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UrlShorteningServiceApplication {

    //TODO
    // <<DONE>>
    // - CRUD API under /shorten (create, get, stats, update, delete)
    // - PostgreSQL + JPA, timestamps through @PrePersist/@PreUpdate
    // - Records as DTOs, entity as a class
    // - Schema in the repo through Flyway, checked by 'validate'

    //TODO
    // - Redirect endpoints
    // - Count clicks
    // - Short-code collisions
    // - Input validation
    // - Clean error responses
    // - Don't expose the database id
    // ~~~

    // DISPLAY LOGS
    // Get-Content logs\app.log -Wait -Tail 20
    // Get-Content logs\app.log -Wait -Tail 20 | Select-String "Deleting"
    // -Wait -> keeps the command running
    // -Tail -> shows the last 20 lines

    public static void main(String[] args) {
        SpringApplication.run(UrlShorteningServiceApplication.class, args);
    }

}
