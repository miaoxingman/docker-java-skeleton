package com.miaoxingman.docker.client.command;

import com.sun.jersey.api.client.WebResource;
import com.google.common.base.Preconditions;

public abstract class AbstrDockerCmd<T extends AbstrDockerCmd<T, RES_T>, RES_T> implements DockerCmd<RES_T> {

    protected WebResource baseResource;

    @SuppressWarnings("unchecked")
    public T WithResource(WebResource resource) {
        baseResource = resource;
        return (T)this;
    }

    abstract RES_T impl();

    @Override
    public RES_T exec() {
        Preconditions.checkNotNull(baseResource, "baseResource was not specified");
        return impl();
    }
}
