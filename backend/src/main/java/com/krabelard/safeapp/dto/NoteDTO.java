package com.krabelard.safeapp.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record NoteDTO(
        UUID uuid,
        String name,
        UserDTO owner
) {
}
