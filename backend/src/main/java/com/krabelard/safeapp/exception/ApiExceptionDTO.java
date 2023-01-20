package com.krabelard.safeapp.exception;

import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
public record ApiExceptionDTO(
        String message,
        ZonedDateTime timestamp
) {
    public static ApiExceptionDTO of(String message) {
        return ApiExceptionDTO.builder()
                .message(message)
                .timestamp(ZonedDateTime.now())
                .build();
    }
}
