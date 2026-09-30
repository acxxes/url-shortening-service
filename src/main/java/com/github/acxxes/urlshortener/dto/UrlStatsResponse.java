package com.github.acxxes.urlshortener.dto;

import java.time.Instant;

// data transfer object
// special class whose job is to carry data
public record UrlStatsResponse(long id,
                               String url,
                               String shortCode,
                               Instant createdAt,
                               Instant updatedAt,
                               long accessCount) {
}
