package com.adh.exchange;

public class EncryptionException extends RuntimeException {

    public EncryptionException() {
        // there should not be a exception with no parameters
        throw new RuntimeException("Illegal configuration");
    }
}
