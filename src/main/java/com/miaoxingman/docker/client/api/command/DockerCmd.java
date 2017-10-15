package com.miaoxingman.docker.client.api.command;

public interface DockerCmd<RES_T> {
    public RES_T exec();
}
