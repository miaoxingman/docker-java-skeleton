package com.miaoxingman.docker.client.core.command;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.miaoxingman.docker.client.api.model.Version;
import com.miaoxingman.docker.client.core.DockerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

public class VersionCmd extends AbstrDockerCmd<VersionCmd, Version> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VersionCmd.class);

    protected Version impl() throws DockerException {
        WebResource webResource = baseResource.path("/version");
        try {
            LOGGER.trace("GET: {}", webResource);
            return webResource.accept(MediaType.APPLICATION_JSON).get(Version.class);
        } catch (UniformInterfaceException exception) {
            if (exception.getResponse().getStatus() == 500) {
                throw new DockerException("Server error.", exception);
            } else {
                throw new DockerException(exception);
            }
        }
    }
}
