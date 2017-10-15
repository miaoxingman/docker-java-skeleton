package com.miaoxingman.docker.client.api;

import java.io.Closeable;
import java.io.IOException;

import com.miaoxingman.docker.client.api.command.VersionCmd;

public interface DockerClient extends Closeable {
    public VersionCmd versionCmd();
    public void close() throws IOException;
}
