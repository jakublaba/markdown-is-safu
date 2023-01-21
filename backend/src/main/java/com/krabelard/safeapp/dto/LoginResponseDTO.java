package com.krabelard.safeapp.dto;

import lombok.Builder;


@Builder
public record LoginResponseDTO(
        String username,
        String email,
        String jwt
) {
}
