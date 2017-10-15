package com.miaoxingman.docker.client.api;

public class NotAcceptableException extends DockerException {
    private static final long serialVersionUID = -6096900575943847973L;

    public NotAcceptableException(String message, Throwable cause) {
        super(message, 406, cause);
    }
    
    public NotAcceptableException(String message) {
        this(message, null);
    }
    
    public NotAcceptableException(Throwable cause) {
        this(cause.getMessage(), cause);
    }
}
