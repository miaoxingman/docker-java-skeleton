package com.miaoxingman.docker.client.api;

public class InternalServerErrorException extends DockerException {

    private static final long serialVersionUID = 2418285931422497478L;

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, 500, cause);
    }
    
    public InternalServerErrorException(String message) {
        this(message, null);
    }
    
    public InternalServerErrorException(Throwable cause) {
        this(cause.getMessage(), cause);
    }
}
