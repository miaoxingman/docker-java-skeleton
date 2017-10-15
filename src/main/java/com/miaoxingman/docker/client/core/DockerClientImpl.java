package com.miaoxingman.docker.client.core;

import java.io.Closeable;
import java.io.IOException;

import com.google.common.base.Preconditions;

import com.miaoxingman.docker.client.core.DockerClientConfig;
import com.miaoxingman.docker.client.core.command.VersionCmdImpl;
import com.miaoxingman.docker.client.jaxrs.DockerCmdExecFactoryImpl;
import com.miaoxingman.docker.client.api.command.VersionCmd;

public class DockerClientImpl implements Closeable, com.miaoxingman.docker.client.api.DockerClient{

    private final DockerClientConfig dockerClientConfig;
    private DockerCmdExecFactoryImpl dockerCmdExecFactory;

    public DockerClientImpl() {
        this(DockerClientConfig.createDefaultConfigBuilder().build());
    }

    public DockerClientImpl(String serverUrl) {
        this(configWithServerUrl(serverUrl));
    }

    private static DockerClientConfig configWithServerUrl(String serverUrl) {
        return DockerClientConfig.createDefaultConfigBuilder()
                .withUri(serverUrl)
                .build();
    }

    public DockerClientImpl(DockerClientConfig dockerClientConfig) {
        Preconditions.checkNotNull(dockerClientConfig, "config was not specified");
        this.dockerClientConfig = dockerClientConfig;
        setDockerCmdExecFactory(new DockerCmdExecFactoryImpl());
    }

    public void setDockerCmdExecFactory(
            DockerCmdExecFactoryImpl dockerCmdExecFactory) {
        Preconditions.checkNotNull(dockerCmdExecFactory, "dockerCmdExecFactory was not specified");
        this.dockerCmdExecFactory = dockerCmdExecFactory;
        this.dockerCmdExecFactory.init(dockerClientConfig);
    }
    
    public DockerCmdExecFactoryImpl getDockerCmdExecFactory() {
        return dockerCmdExecFactory;
    }

    @Override
    public VersionCmd versionCmd() {
        return new VersionCmdImpl(getDockerCmdExecFactory().createVersionCmdExec());
    }

    @Override
    public void close() throws IOException {
        getDockerCmdExecFactory().close();
    }

}
