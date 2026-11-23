package com.github.acxxes.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

// data transfer object
// special class whose job is to carry data
// POST, PUT
public record UrlRequest(
        @NotBlank @Size(max = 2000)
        @URL(regexp = "^(http|https)://.*",
                flags = Pattern.Flag.CASE_INSENSITIVE,
                message = "URL protocol's that are allowed -> 'http, https'")
        String url) {
}
