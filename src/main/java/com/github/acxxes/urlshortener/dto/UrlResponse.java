package com.github.acxxes.urlshortener.dto;

import com.github.acxxes.urlshortener.entity.Url;

import java.time.Instant;

// data transfer object
// special class whose job is to carry data
// POST, GET, PUT
public record UrlResponse(long id,
                          String url,
                          String shortCode,
                          Instant createdAt,
                          Instant updatedAt) {

    public static UrlResponse from(Url url) {
        return new UrlResponse(
                url.getId(),
                url.getUrl(),
                url.getShortCode(),
                url.getCreatedAt(),
                url.getUpdatedAt()
        );
    }

}
