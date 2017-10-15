package com.miaoxingman.docker.client.core.command;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.miaoxingman.docker.client.api.DockerException;
import com.miaoxingman.docker.client.api.model.Image;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

//TODO: implement this class
public class ListImagesCmdImpl 
{}
/*
extends AbstrDockerCmd<ListImagesCmdImpl, List<Image>> implements ListImagesCmd {
    private String filter;
    private boolean showAll = false;
    
    public ListImagesCmdImpl(ListImagesCmd.Exec exec) {
        super(exec);
    }
}
*/