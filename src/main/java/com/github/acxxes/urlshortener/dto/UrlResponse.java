package com.github.acxxes.urlshortener.dto;

import java.time.Instant;

// data transfer object
// special class whose job is to carry data
// POST, GET, PUT
public record UrlResponse(int id,
                          String url,
                          String shortCode,
                          Instant createdAt,
                          Instant updatedAt) {
}
