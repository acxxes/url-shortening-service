package com.github.acxxes.urlshortener.dto;

import com.github.acxxes.urlshortener.entity.URL;

import java.time.Instant;

// data transfer object
// special class whose job is to carry data
public record URLStatsResponse(long id,
                               String url,
                               String shortCode,
                               Instant createdAt,
                               Instant updatedAt,
                               long accessCount) {

    public static URLStatsResponse from(URL url) {
        return new URLStatsResponse(
                url.getId(),
                url.getUrl(),
                url.getShortCode(),
                url.getCreatedAt(),
                url.getUpdatedAt(),
                url.getAccessCount()
        );
    }

}
