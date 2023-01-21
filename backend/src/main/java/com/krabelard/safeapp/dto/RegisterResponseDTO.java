package com.krabelard.safeapp.dto;


import lombok.Builder;

@Builder
public record RegisterResponseDTO(
        String username,
        String email
) {
}
