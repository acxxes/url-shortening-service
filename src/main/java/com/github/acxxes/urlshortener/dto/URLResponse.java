package com.github.acxxes.urlshortener.dto;

import com.github.acxxes.urlshortener.entity.URL;

import java.time.Instant;

// data transfer object
// special class whose job is to carry data
// POST, GET, PUT
public record URLResponse(long id,
                          String url,
                          String shortCode,
                          Instant createdAt,
                          Instant updatedAt) {

    public static URLResponse from(URL url) {
        return new URLResponse(
                url.getId(),
                url.getUrl(),
                url.getShortCode(),
                url.getCreatedAt(),
                url.getUpdatedAt()
        );
    }

}
