package main.dto;

import java.time.Instant;

// data transfer object
// special class whose job is to carry data
public record UrlStatsResponse(int id,
                               String url,
                               String shortCode,
                               Instant createdAt,
                               Instant updatedAt,
                               int accessCount) {
}
