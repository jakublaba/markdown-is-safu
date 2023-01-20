package com.krabelard.safeapp.exception.note;

import java.util.UUID;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(UUID uuid) {
        super(String.format("[%s] - not not found", uuid));
    }

}
