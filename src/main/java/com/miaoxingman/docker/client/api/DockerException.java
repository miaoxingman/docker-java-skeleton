package com.miaoxingman.docker.client.api;

public class DockerException extends RuntimeException {

    private static final long serialVersionUID = 5667009376291387834L;
    private int httpStatus = 0;

    public DockerException(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public DockerException(String message, int httpStatus, Throwable cause) {
        super(message, cause);
    }
    
    public int getHttpStatus() {
        return httpStatus;
    }
}
