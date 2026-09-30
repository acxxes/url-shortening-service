//package com.github.acxxes.urlshortener.controller;
//
//import com.github.acxxes.urlshortener.dto.UrlRequest;
//import com.github.acxxes.urlshortener.dto.UrlResponse;
//import com.github.acxxes.urlshortener.service.UrlService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/shorten") // common base path for all methods
//public class UrlController {
//    private final UrlService urlService;
//
//    public UrlController(UrlService urlService) {
//        this.urlService = urlService;
//    }
//
//    // POST /shorten
//    @PostMapping
//    public ResponseEntity<UrlResponse> create(@RequestBody UrlRequest request) {
//        // WIP
//    }
//
//    // GET /shorten/{shortCode}
//    @GetMapping("/{shortCode}")
//    public UrlResponse get(@PathVariable String shortCode) {
//        // WIP
//    }
//
//}
