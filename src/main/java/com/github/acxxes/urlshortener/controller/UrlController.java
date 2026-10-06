package com.github.acxxes.urlshortener.controller;

import com.github.acxxes.urlshortener.dto.UrlRequest;
import com.github.acxxes.urlshortener.dto.UrlResponse;
import com.github.acxxes.urlshortener.dto.UrlStatsResponse;
import com.github.acxxes.urlshortener.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shorten") // common base path for all methods
public class UrlController {
    private final UrlService urlService;

    // Spring decides based on HTTP method + path what to choose
    // @RequestBody Jackson reads JSON and calls the records constructor

    /*
    @PathVariable = identifies an existing thing (short, simple, no /)
    @RequestBody = data being sent (can contain anything)
     */

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    /**
     * Returns the short URL entry for the given code.
     * <p>
     * Responds with 200, or 404 if the code does not exist.
     * <p>
     * Execute in the terminal with:
     * <pre>{@code
     * curl.exe -i http://localhost:8080/shorten/{shortCode}
     * }</pre>
     *
     * @param shortCode the short code from the request path
     * @return the entry, as {@link UrlResponse} 
     */
    @GetMapping("/{shortCode}")
    public UrlResponse get(@PathVariable String shortCode) {
        return urlService.findByShortCode(shortCode);
    }

    /**
     * Returns the short URL entry with {@code accessCount} for the given code.
     * <p>
     * Responds with 200, or 404 if the code does not exist.
     * <p>
     * Execute in the terminal with:
     * <pre>{@code
     * curl.exe -i http://localhost:8080/shorten/{shortCode}/stats
     * }</pre>
     *
     * @param shortCode the short code from the request path
     * @return {@link UrlStatsResponse} the entry including its access count
     */
    @GetMapping("/{shortCode}/stats")
    public UrlStatsResponse getStats(@PathVariable String shortCode) {
        return urlService.findByShortCodeWithStats(shortCode);
    }

    /**
     * Creates the short code for the given URL and adds an entry to the database.
     * <p>
     * Responds with 201.
     * <p>
     * Execute in the terminal with:
     * <pre>{@code
     * curl.exe -i -X POST http://localhost:8080/shorten -H "Content-Type: application/json" -d "@http-tests/create.json"
     * }</pre>
     * Works with PowerShell 7.3+ (check version: pwsh --version):
     * <pre>{@code
     * curl.exe -i -X POST http://localhost:8080/shorten -H "Content-Type: application/json" -d '{"url":"https://example.com"}'
     * }</pre>
     *
     * @param urlRequest the JSON body, containing the long URL to shorten
     * @return {@link UrlResponse} the created entry including its generated short code
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UrlResponse create(@RequestBody UrlRequest urlRequest) {
        return urlService.createShortUrl(urlRequest.url());
    }

    /**
     * Deletes the entry from the database for the given code.
     * <p>
     * Responds with 204, or 404 if the code does not exist.
     * <p>
     * Execute in the terminal with:
     * <pre>{@code
     * curl.exe -i -X DELETE http://localhost:8080/shorten/{shortCode}
     * }</pre>
     *
     * @param shortCode the short code from the request path
     */
    @DeleteMapping("/{shortCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String shortCode) {
        urlService.deleteByShortCode(shortCode);
    }

    //TODO WIP

    // PUT /shorten/{shortCode}
//    @PutMapping("/{shortCode}")
//    public UrlResponse update(@PathVariable String shortCode) {
//    }

}
