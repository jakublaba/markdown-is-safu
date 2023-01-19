package com.krabelard.safeapp.exception.user;

import com.krabelard.safeapp.controller.UserController;
import com.krabelard.safeapp.exception.ApiExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = UserController.class)
public class UserExceptionHandler {

    @ExceptionHandler({InvalidUsernameException.class, InvalidEmailException.class, InsecurePasswordException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiExceptionDTO handle400(Exception e) {
        return ApiExceptionDTO.of(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiExceptionDTO handle404(Exception e) {
        return ApiExceptionDTO.of(e.getMessage());
    }

    @ExceptionHandler({UsernameTakenException.class, EmailTakenException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiExceptionDTO handle409(Exception e) {
        return ApiExceptionDTO.of(e.getMessage());
    }

}