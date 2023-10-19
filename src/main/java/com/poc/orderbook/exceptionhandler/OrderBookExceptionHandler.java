package com.poc.orderbook.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderBookExceptionHandler {

    @ExceptionHandler({BaseException.class})
    public ResponseEntity<Object> handleBaseException(BaseException be) {
        return new ResponseEntity<>(be.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
