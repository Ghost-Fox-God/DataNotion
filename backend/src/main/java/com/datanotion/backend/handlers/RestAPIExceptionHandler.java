package com.datanotion.backend.handlers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.datanotion.backend.exceptions.BadRequestException;
import com.datanotion.backend.exceptions.InternalServerErrorException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestAPIExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequest(
            BadRequestException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<Object>(
                apiError, apiError.getStatus());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    protected ResponseEntity<Object> handleInternalServerError(
            InternalServerErrorException ex) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<Object>(
                apiError, apiError.getStatus());
    }
}
