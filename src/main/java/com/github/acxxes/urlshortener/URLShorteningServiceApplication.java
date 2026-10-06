package com.github.acxxes.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class URLShorteningServiceApplication {

    //TODO
    // - learn PostgreSQL, establish API with the 5 queries behind HTTP
    // - make the access_count work

    public static void main(String[] args) {
        SpringApplication.run(URLShorteningServiceApplication.class, args);
    }

//    @Bean
//    CommandLineRunner demo(UrlRepository repository) {
//        return args -> {
//            Url saved = repository.save(new Url("https://iom-arch-optimizer-web.pages.dev/#/welcome", "optimizer"));
//            System.out.println("Saved with id: " + saved.getId());
//
//            repository.findByShortCode("optimizer")
//                    .ifPresent(url -> System.out.println("Url: " + url.getUrl() + ", created " + url.getCreatedAt()));
//        };
//    }

}
