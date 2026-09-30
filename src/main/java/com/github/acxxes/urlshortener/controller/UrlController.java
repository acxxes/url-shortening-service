package com.github.acxxes.urlshortener.controller;

import com.github.acxxes.urlshortener.dto.UrlRequest;
import com.github.acxxes.urlshortener.dto.UrlResponse;
import com.github.acxxes.urlshortener.dto.UrlStatsResponse;
import com.github.acxxes.urlshortener.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shorten") // common base path for all methods
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    // Spring decides based on HTTP method + path what to choose

    // GET /shorten
    @GetMapping

    // GET /shorten/{shortCode}
    @GetMapping("/{shortCode}")
    public UrlResponse get(@PathVariable String shortCode) {
        return urlService.findByShortCode(shortCode);
    }

    // GET /shorten/{shortCode}/status
    @GetMapping("/{shortCode}/status")
    public UrlStatsResponse get(@PathVariable String shortCode) {
    }

    // POST /url
    // @RequestBody Jackson reads JSON and calls the records constructor
    @PostMapping
    public ResponseEntity<UrlResponse> create(@RequestBody UrlRequest request) {
    }

    // PUT /shorten/{shortCode}
    @PutMapping("/{shortCode}")

    // PATCH /shorten/{shortCode} (partial update, may not use is)
    @PatchMapping("/{shortCode}")

    // DELETE /shorten/{shortCode}
    @DeleteMapping("/{shortCode}")


}
