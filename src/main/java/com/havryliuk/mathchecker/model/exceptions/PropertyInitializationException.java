package com.havryliuk.mathchecker.model.exceptions;

public class PropertyInitializationException extends RuntimeException {

    public PropertyInitializationException() {
        super();
    }

    public PropertyInitializationException(String message) {
        super(message);
    }

    public PropertyInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertyInitializationException(Throwable cause) {
        super(cause);
    }
}
