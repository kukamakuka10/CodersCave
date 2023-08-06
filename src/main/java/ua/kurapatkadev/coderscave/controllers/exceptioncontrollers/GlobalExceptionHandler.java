package ua.kurapatkadev.coderscave.controllers.exceptioncontrollers;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.kurapatkadev.coderscave.dto.exception.AppError;
import ua.kurapatkadev.coderscave.exceptions.PasswordConfirmationException;
import ua.kurapatkadev.coderscave.exceptions.UsernameAlreadyExistsException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public AppError handleBadCredentialsException(BadCredentialsException e) {
        return new AppError(HttpStatus.UNAUTHORIZED.value(), e.getMessage() + " No Correct login or password ");
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AppError handleUsernameAlreadyExistsException(UsernameAlreadyExistsException e) {
        return new AppError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(PasswordConfirmationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AppError handlePasswordConfirmationException(PasswordConfirmationException e) {
        return new AppError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public AppError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new AppError(HttpStatus.BAD_REQUEST.value(), e.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .orElse("Unknown error"));
    }

}
