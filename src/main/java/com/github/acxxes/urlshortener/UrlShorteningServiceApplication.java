package com.github.acxxes.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UrlShorteningServiceApplication {

    //TODO
    // - learn PostgreSQL, establish API with the 5 queries behind HTTP
    // - make the access_count work

    public static void main(String[] args) {
        SpringApplication.run(UrlShorteningServiceApplication.class, args);
    }

}
