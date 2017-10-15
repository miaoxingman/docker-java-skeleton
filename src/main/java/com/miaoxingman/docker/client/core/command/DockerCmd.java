package com.miaoxingman.docker.client.core.command;

public interface DockerCmd<RES_T> {
    public RES_T exec();
}