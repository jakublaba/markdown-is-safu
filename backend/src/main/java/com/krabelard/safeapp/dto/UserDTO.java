package com.krabelard.safeapp.dto;

import lombok.Builder;

@Builder
public record UserDTO(
        String username,
        String email
) {
}
