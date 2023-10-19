package com.poc.orderbook.exceptionhandler;

public class BaseException extends Exception{

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }
}
