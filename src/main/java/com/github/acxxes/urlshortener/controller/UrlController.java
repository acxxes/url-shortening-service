package com.github.acxxes.urlshortener.controller;

import com.github.acxxes.urlshortener.dto.UrlRequest;
import com.github.acxxes.urlshortener.dto.UrlResponse;
import com.github.acxxes.urlshortener.dto.UrlStatsResponse;
import com.github.acxxes.urlshortener.service.UrlService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shorten") // common base path for all methods
public class UrlController {
    private final UrlService urlService;

    // Spring decides based on HTTP method + path what to choose

    /*
    @PathVariable = identifies an existing thing (short, simple, no /)
    @RequestBody = data being sent (can contain anything)
     */

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    // GET /shorten/{shortCode}
    // curl: curl.exe -i http://localhost:8080/shorten/{shortCode}
    @GetMapping("/{shortCode}")
    public UrlResponse get(@PathVariable String shortCode) {
        return urlService.findByShortCode(shortCode);
    }

    // GET /shorten/{shortCode}/stats
    // curl: curl.exe -i http://localhost:8080/shorten/{shortCode}/stats
    @GetMapping("/{shortCode}/stats")
    public UrlStatsResponse getStats(@PathVariable String shortCode) {
        return urlService.findByShortCodeWithStats(shortCode);
    }

    // curl: curl.exe -i -X POST http://localhost:8080/shorten -H "Content-Type: application/json" -d "@http-tests/create.json"
    // works with PowerShell 7.3+ (check version: pwsh --version)
    // curl: curl.exe -i -X POST http://localhost:8080/shorten -H "Content-Type: application/json" -d '{"url":"https://example.com"}'
    // POST /shorten
    // @RequestBody Jackson reads JSON and calls the records constructor
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // returns 201
    public UrlResponse create(@RequestBody UrlRequest urlRequest) {
        return urlService.createShortUrl(urlRequest.url());
    }
    /*
    JSON → @RequestBody → UrlRequest → controller unpacks url()
     → service: generate code → new Url → save() → UrlResponse.from()
     → controller: @ResponseStatus(CREATED) → Jackson → JSON
     */

    // PUT /shorten/{shortCode}
//    @PutMapping("/{shortCode}")
//    public UrlResponse update(@PathVariable String shortCode) {
//    }

    // curl.exe -i -X DELETE http://localhost:8080/shorten/{shortCode}
    // DELETE /shorten/{shortCode}

    /**
     * Deletes url by given shortCode
     * @param shortCode
     * Input the generated shortCode given from the database
     * <p>
     * curl.exe -i -X DELETE http://localhost:8080/shorten/{shortCode}
     */
    @DeleteMapping("/{shortCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // returns 204
    public void delete(@PathVariable String shortCode) {
        urlService.deleteByShortCode(shortCode);
    }

}
