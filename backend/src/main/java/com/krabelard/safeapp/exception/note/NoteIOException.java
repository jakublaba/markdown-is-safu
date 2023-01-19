package com.krabelard.safeapp.exception.note;

public class NoteIOException extends RuntimeException {

    public NoteIOException(String name) {
        super(String.format("Unexpected error while processing note [%s]", name));
    }

}
