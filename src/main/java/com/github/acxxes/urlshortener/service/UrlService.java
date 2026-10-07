package com.github.acxxes.urlshortener.service;

import com.github.acxxes.urlshortener.dto.UrlResponse;
import com.github.acxxes.urlshortener.dto.UrlStatsResponse;
import com.github.acxxes.urlshortener.entity.Url;
import com.github.acxxes.urlshortener.repository.UrlRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class UrlService {
    private static final Logger log = LoggerFactory.getLogger(UrlService.class);
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public UrlResponse findByShortCode(String shortCode) {
        return UrlResponse.from(findUrlOrThrow(shortCode));
    }

    public UrlStatsResponse findByShortCodeWithStats(String shortCode) {
        return UrlStatsResponse.from(findUrlOrThrow(shortCode));
    }

    public UrlResponse createShortUrl(String url) {
        return UrlResponse.from(newUrlDatabaseEntry(url));
    }

    @Transactional
    public UrlResponse updateUrl(String shortCode, String newUrl) {
        Url url = findUrlOrThrow(shortCode);
        url.setUrl(newUrl);
        // guarantees flush before the mapping, so updatedAt gets updated before the next GET request
        urlRepository.flush();
        return UrlResponse.from(url);
    }

    @Transactional
    public void deleteByShortCode(String shortCode) {
        Url url = findUrlOrThrow(shortCode);
        log.info("Deleting URL: {}", UrlResponse.from(url)); // {} is replaced by the argument
        urlRepository.deleteByShortCode(shortCode);
    }

    //TODO
    // - phase 6
    private String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 5);
    }

    private Url findUrlOrThrow(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Short code not found: " + shortCode
                        )
                );
    }

    private Url newUrlDatabaseEntry(String url) {
        return urlRepository.save(new Url(url, generateShortCode()));
    }

}
