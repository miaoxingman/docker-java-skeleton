package com.miaoxingman.docker.client.jaxrs;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.common.base.Preconditions;

import com.miaoxingman.docker.client.api.command.DockerCmdExecFactory;
import com.miaoxingman.docker.client.api.command.VersionCmd;
import com.miaoxingman.docker.client.api.command.VersionCmd.Exec;
import com.miaoxingman.docker.client.core.DockerClientConfig;
import com.miaoxingman.docker.client.core.JsonClientFilter;
import com.miaoxingman.docker.client.core.SelectiveLoggingFilter;

public class DockerCmdExecFactoryImpl implements DockerCmdExecFactory {

    private Client jerseyClient;
    private WebTarget baseTarget;

    @Override
    public void init(DockerClientConfig dockerClientConfig) {
        Preconditions.checkNotNull(dockerClientConfig, "config was not specified");

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(JsonClientFilter.class);
        clientConfig.register(JacksonJsonProvider.class);

        if (dockerClientConfig.isLoggingFilterEnabled()) {
            clientConfig.register(SelectiveLoggingFilter.class);
        }

        if (dockerClientConfig.getReadTimeout() != null) {
            int readTimeout = dockerClientConfig.getReadTimeout();
            clientConfig.property(ClientProperties.READ_TIMEOUT, readTimeout);
        }
        jerseyClient = ClientBuilder.newClient(clientConfig);

        WebTarget webTarget = jerseyClient.target(dockerClientConfig.getUri());
        if (dockerClientConfig.getVersion() != null) {
            baseTarget = webTarget.path("v" + dockerClientConfig.getVersion());
        } else {
            baseTarget = webTarget;
        }
    }

    private WebTarget getBaseResource() {
        Preconditions.checkNotNull(baseTarget,
                "Factory not initialized. You probably forgot to call init()!");
        return baseTarget;
    }

    @Override
    public VersionCmd.Exec createVersionCmdExec() {
        return new VersionCmdExec(getBaseResource());
    }

    @Override
    public void close() throws IOException {
        Preconditions.checkNotNull(jerseyClient,
                "Factory not initialized. You probably forgot to call init()!");
        jerseyClient.close();
    }
}