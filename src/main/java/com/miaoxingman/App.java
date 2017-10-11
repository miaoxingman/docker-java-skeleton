package com.miaoxingman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */

import com.miaoxingman.docker.client.DockerClient;
import com.miaoxingman.docker.client.DockerException;

import com.sun.jersey.api.client.ClientResponse;

public class App 
{
    public static final Logger LOG = LoggerFactory.getLogger(App.class);
    private DockerClient dockerClient;

    public App() {
        dockerClient = new DockerClient("http://10.117.162.153:2375");
    }

    public void pullUbuntuImage() {
        try {
            ClientResponse response = dockerClient.pull("ubuntu");
            LOG.debug("resposne {}", response.toString());
        } catch (DockerException e) {
            LOG.debug(e.getMessage());
        }
    }

    public static void main( String[] args )
    {
        App app = new App();
        LOG.debug("Hello World!");
        app.pullUbuntuImage();
    }
}
