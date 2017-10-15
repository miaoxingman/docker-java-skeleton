package com.miaoxingman.docker.client.jaxrs;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.miaoxingman.docker.client.api.command.VersionCmd;
import com.miaoxingman.docker.client.api.model.Version;

public class VersionCmdExec extends AbstrDockerCmdExec<VersionCmd, Version> implements VersionCmd.Exec {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(VersionCmdExec.class);

    public VersionCmdExec(WebTarget baseResource) {
        super(baseResource);
    }

    @Override
    public Version exec(VersionCmd command) {
        WebTarget webResource = getBaseResource().path("/version");

        LOGGER.trace("GET: {}", webResource);
        return webResource.request().accept(MediaType.APPLICATION_JSON)
                .get(Version.class);
    }
}
