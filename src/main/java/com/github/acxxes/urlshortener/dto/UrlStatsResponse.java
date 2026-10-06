package com.github.acxxes.urlshortener.dto;

import com.github.acxxes.urlshortener.entity.Url;

import java.time.Instant;

// data transfer object
// special class whose job is to carry data
public record UrlStatsResponse(long id,
                               String url,
                               String shortCode,
                               Instant createdAt,
                               Instant updatedAt,
                               long accessCount) {

    public static UrlStatsResponse from(Url url) {
        return new UrlStatsResponse(
                url.getId(),
                url.getUrl(),
                url.getShortCode(),
                url.getCreatedAt(),
                url.getUpdatedAt(),
                url.getAccessCount()
        );
    }

}
