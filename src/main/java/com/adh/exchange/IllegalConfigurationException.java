package com.adh.exchange;

public class IllegalConfigurationException extends RuntimeException {

    public IllegalConfigurationException() {
        super();
    }

    public IllegalConfigurationException(final String message) {
        super(message);
    }
}
