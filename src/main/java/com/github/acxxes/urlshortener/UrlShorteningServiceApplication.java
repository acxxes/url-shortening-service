package com.github.acxxes.urlshortener;

import com.github.acxxes.urlshortener.entity.Url;
import com.github.acxxes.urlshortener.repository.UrlRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UrlShorteningServiceApplication {

    //TODO
    // - learn postgreSQL, establish API with the 5 queries behind HTTP

    public static void main(String[] args) {
        SpringApplication.run(UrlShorteningServiceApplication.class, args);
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
