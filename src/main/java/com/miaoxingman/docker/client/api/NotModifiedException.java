package com.miaoxingman.docker.client.api;

public class NotModifiedException extends DockerException {

    private static final long serialVersionUID = -3392013078900560087L;

    public NotModifiedException(String message, Throwable cause) {
        super(message, 304, cause);
    }
    
    public NotModifiedException(String message) {
        this(message, null);
    }
    
    public NotModifiedException(Throwable cause) {
        this(cause.getMessage(), cause);
    }
}
