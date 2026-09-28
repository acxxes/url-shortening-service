package com.github.acxxes.urlshortener.service;

import com.github.acxxes.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Service;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

}
