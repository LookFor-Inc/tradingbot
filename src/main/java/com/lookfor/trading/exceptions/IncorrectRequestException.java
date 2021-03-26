package com.lookfor.trading.exceptions;

/**
 * Incorrect country exception
 */
public class IncorrectRequestException extends RuntimeException {
    public IncorrectRequestException(String message) {
        super(message);
    }
}
