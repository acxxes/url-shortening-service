package com.github.acxxes.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UrlShorteningServiceApplication {

    //TODO
    // - learn PostgreSQL, establish API with the 5 queries behind HTTP
    // - make the access_count work

    // DISPLAY LOGS
    // Get-Content logs\app.log -Wait -Tail 20
    // Get-Content logs\app.log -Wait -Tail 20 | Select-String "Deleting"
    // -Wait -> keeps the command running
    // -Tail -> shows the last 20 lines

    public static void main(String[] args) {
        SpringApplication.run(UrlShorteningServiceApplication.class, args);
    }

}
