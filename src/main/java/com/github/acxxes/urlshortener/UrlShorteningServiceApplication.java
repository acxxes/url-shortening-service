package com.github.acxxes.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UrlShorteningServiceApplication {

    //TODO
    // ~~~

    // DISPLAY LOGS
    // Get-Content logs\app.log -Wait -Tail 20
    // Get-Content logs\app.log -Wait -Tail 20 | Select-String "Deleting"wse
    // -Wait -> keeps the command running
    // -Tail -> shows the last 20 lines

    public static void main(String[] args) {
        SpringApplication.run(UrlShorteningServiceApplication.class, args);
    }

}
