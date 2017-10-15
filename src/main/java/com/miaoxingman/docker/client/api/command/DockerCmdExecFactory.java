package com.miaoxingman.docker.client.api.command;

import java.io.Closeable;
import java.io.IOException;

import com.miaoxingman.docker.client.api.command.VersionCmd;
import com.miaoxingman.docker.client.core.DockerClientConfig;

public interface DockerCmdExecFactory extends Closeable {
    public void init(DockerClientConfig dockerClientConfig);
    public VersionCmd.Exec createVersionCmdExec();
    public void close() throws IOException;

}
