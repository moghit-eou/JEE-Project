package com.denbondd.restaurant.exceptions;

/**
 * Custom not checked exception
 * Used for displaying error page on website
 */
public class AppException extends RuntimeException {
    public AppException() {
    }
    public AppException(String message) {
        super(message);
    }
    public AppException(Throwable cause) {
        super(cause);
    }


}
