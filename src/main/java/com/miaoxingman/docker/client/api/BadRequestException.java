package com.miaoxingman.docker.client.api;

public class BadRequestException extends DockerException {

    private static final long serialVersionUID = 194440565093531637L;

    public BadRequestException(String message, Throwable cause) {
        super(message, 400, cause);
    }
    
    public BadRequestException(String message) {
        this(message, null);
    }
    
    public BadRequestException(Throwable cause) {
        this(cause.getMessage(), cause);
    }
}
