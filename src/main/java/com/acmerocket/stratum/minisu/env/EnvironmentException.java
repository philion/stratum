package com.acmerocket.stratum.minisu.env;

public class EnvironmentException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EnvironmentException(String errorMessage) {
        super(errorMessage);
    }
}
