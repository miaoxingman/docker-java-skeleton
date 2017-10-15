package com.miaoxingman.docker.client.api;

public class ConflictException extends DockerException {

    private static final long serialVersionUID = 7082752388969747399L;

    public ConflictException(String message, Throwable cause) {
        super(message, 409, cause);
    }
    
    public ConflictException(String message) {
        this(message, null);
    }
    
    public ConflictException(Throwable cause) {
        this(cause.getMessage(), cause);
    }
}
