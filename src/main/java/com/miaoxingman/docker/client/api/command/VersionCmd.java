package com.miaoxingman.docker.client.api.command;

import com.miaoxingman.docker.client.api.command.DockerCmdExec;
import com.miaoxingman.docker.client.api.command.VersionCmd;
import com.miaoxingman.docker.client.api.model.Version;

public interface VersionCmd extends DockerCmd<Version> {
    public static interface Exec extends DockerCmdExec<VersionCmd, Version> {
    }
}