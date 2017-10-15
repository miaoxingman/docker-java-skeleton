package com.miaoxingman.docker.client.api;

public class NotFoundException extends DockerException {

    private static final long serialVersionUID = -1799948196777566006L;

    public NotFoundException(String message, Throwable cause) {
        super(message, 404, cause);
    }
    
    public NotFoundException(String message) {
        this(message, null);
    }
    
    public NotFoundException(Throwable cause) {
        this(cause.getMessage(), cause);
    }
}
