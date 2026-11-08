package com.github.acxxes.urlshortener.exception;

public class ShortCodeNotFoundException extends RuntimeException {
    public ShortCodeNotFoundException(String shortCode) {
        super("The short code:[" + shortCode + "] does not exist");
    }
}
