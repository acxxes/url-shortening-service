package com.github.acxxes.urlshortener.service;

import com.github.acxxes.urlshortener.dto.URLResponse;
import com.github.acxxes.urlshortener.dto.URLStatsResponse;
import com.github.acxxes.urlshortener.entity.URL;
import com.github.acxxes.urlshortener.repository.URLRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class URLService {
    private final URLRepository urlRepository;

    public URLService(URLRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    // GET http://localhost:8080/shorten/{shortCode}
    public URLResponse findByShortCode(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .map(URLResponse::from)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Short_Code not found: " + shortCode
                ));
    }

    // GET http://localhost:8080/shorten/{shortCode}/stats
    public URLStatsResponse findByShortCodeWithStats(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .map(URLStatsResponse::from)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Short_Code not found: " + shortCode
                ));
    }

    // POST http://localhost:8080/shorten
    public URLResponse createShortUrl(String url) {
        String generatedShortCode = generateShortCode();
        URL saved = urlRepository.save(new URL(url, generatedShortCode));
        return URLResponse.from(saved);
    }

    // PUT http://localhost:8080/shorten/{shortCode}
//    public UrlResponse updateShortUrl(String shortCode, String updatedShortCode) {
//    }

    // DELETE http://localhost:8080/shorten/{shortCode}
    @Transactional
    public void deleteByShortCode(String shortCode){
        urlRepository.deleteByShortCode(shortCode);
    }

    // TODO phase 6
    private String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 5);
    }

}
