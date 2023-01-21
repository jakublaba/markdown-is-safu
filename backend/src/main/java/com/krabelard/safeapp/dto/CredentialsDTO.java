package com.krabelard.safeapp.dto;

import lombok.Builder;


@Builder
public record CredentialsDTO(
        String username,
        String email,
        String credential // either password in registration request or access token in login response
) {
}
