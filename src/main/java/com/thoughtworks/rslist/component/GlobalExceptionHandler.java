package com.thoughtworks.rslist.component;

import com.thoughtworks.rslist.exception.CommenError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.access.InvalidInvocationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({InvalidInvocationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity exceptionHandler(Exception ex){
        String errorMessage;
        CommenError commError =new CommenError();
        if(ex instanceof MethodArgumentNotValidException){
            errorMessage="invalid param";
        }else {
            errorMessage=ex.getMessage();
        }
        commError.setError(errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commError);
    }
}
