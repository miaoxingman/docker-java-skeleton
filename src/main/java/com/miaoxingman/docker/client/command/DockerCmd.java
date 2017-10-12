package com.miaoxingman.docker.client.command;

public interface DockerCmd<RES_T> {
    public RES_T exec();
}