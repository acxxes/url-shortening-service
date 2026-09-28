package main.controller;

import main.dto.UrlRequest;
import main.dto.UrlResponse;
import main.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shorten") // common base path for all methods
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    // POST /shorten
    @PostMapping
    public ResponseEntity<UrlResponse> create(@RequestBody UrlRequest request) {
        // WIP
    }

    // GET /shorten/{short_code}
    @GetMapping("/{short_code}")
    public UrlResponse get(@PathVariable String shortCode) {
        // WIP
    }

}
