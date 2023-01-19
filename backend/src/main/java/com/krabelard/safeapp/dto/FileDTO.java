package com.krabelard.safeapp.dto;

import lombok.Builder;

@Builder
public record FileDTO(
        String fileName,
        byte[] content
) {
}
