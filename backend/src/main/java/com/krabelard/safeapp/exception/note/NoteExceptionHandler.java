package com.krabelard.safeapp.exception.note;

import com.krabelard.safeapp.controller.NoteController;
import com.krabelard.safeapp.exception.ApiExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice(basePackageClasses = NoteController.class)
public class NoteExceptionHandler {

    @ExceptionHandler(NoteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiExceptionDTO handle404(Exception e) {
        return ApiExceptionDTO.of(e.getMessage());
    }

    @ExceptionHandler(NoteConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiExceptionDTO handle409(Exception e) {
        return ApiExceptionDTO.of(e.getMessage());
    }

    @ExceptionHandler({NoSuchElementException.class, NoteIOException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiExceptionDTO handle500(Exception e) {
        return ApiExceptionDTO.of(e.getMessage());
    }

}
