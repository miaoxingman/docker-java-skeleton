package com.miaoxingman.docker.client.jaxrs;

import javax.ws.rs.client.WebTarget;

import com.google.common.base.Preconditions;
import com.miaoxingman.docker.client.api.command.DockerCmd;
import com.miaoxingman.docker.client.api.command.DockerCmdExec;

public abstract class AbstrDockerCmdExec<CMD_T extends DockerCmd<RES_T>, RES_T> implements DockerCmdExec<CMD_T, RES_T> {

    private WebTarget baseTarget;

    public AbstrDockerCmdExec(WebTarget baseResource) {
        Preconditions.checkNotNull(baseResource, "baseResource was not specified");
        this.baseTarget = baseResource;
    }
    
    protected WebTarget getBaseResource() {
        return baseTarget;
    }
}