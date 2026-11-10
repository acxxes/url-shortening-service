package com.github.acxxes.urlshortener.service;

import com.github.acxxes.urlshortener.dto.UrlResponse;
import com.github.acxxes.urlshortener.dto.UrlStatsResponse;
import com.github.acxxes.urlshortener.entity.Url;
import com.github.acxxes.urlshortener.exception.ShortCodeNotFoundException;
import com.github.acxxes.urlshortener.repository.UrlRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.UUID;

@Service
public class UrlService {
    private static final Logger log = LoggerFactory.getLogger(UrlService.class);
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Transactional
    public URI createURIAndUpdateAccessCount(String shortCode) {
        UrlResponse urlResponse = findByShortCode(shortCode);
        String url = urlResponse.url();
        updateAccessCount(shortCode);
        return URI.create(url);
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
        log.info("Deleting URL: id={} {}", url.getId(), UrlResponse.from(url)); // {} is replaced by the argument
        urlRepository.deleteByShortCode(shortCode);
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

    public void updateAccessCount(String shortCode) {
        int count = urlRepository.incrementAccessCount(shortCode);
        System.err.println("Update count by: [" + count + "]");
        if (count == 0) {
            throw new ShortCodeNotFoundException(shortCode);
        }
    }

    private String generateShortCode() {

        return UUID.randomUUID().toString().substring(0, 5);
    }

    private Url findUrlOrThrow(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new ShortCodeNotFoundException(shortCode));
    }

    private Url newUrlDatabaseEntry(String url) {
        return urlRepository.save(new Url(url, generateShortCode()));
    }

}
