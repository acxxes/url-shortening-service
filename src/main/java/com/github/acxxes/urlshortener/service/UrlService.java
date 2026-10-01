package com.github.acxxes.urlshortener.service;

import com.github.acxxes.urlshortener.dto.UrlResponse;
import com.github.acxxes.urlshortener.repository.UrlRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public UrlResponse findByShortCode(String shortCode) {
        return urlRepository.findByShortCode(shortCode)
                .map(UrlResponse::from)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Short_Code not found: " + shortCode
                ));
    }

}
