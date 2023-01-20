package com.krabelard.safeapp.dto;

import lombok.Builder;


@Builder
public record LoginRequestDTO(
        String username,
        String password
) {
}
