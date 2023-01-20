package com.krabelard.safeapp.exception.note;

public class NoteConflictException extends RuntimeException {

    public NoteConflictException(String uuid) {
        super(String.format("Note already exists [%s]", uuid));
    }

}
