package com.miaoxingman.docker.client.test;

import java.io.IOException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestResult;

import com.miaoxingman.docker.client.core.DockerClient;
import com.miaoxingman.docker.client.core.DockerException;
import com.sun.jersey.api.client.ClientResponse;

public class AbstractDockerClientTest extends Assert {

    public static final Logger LOG = LoggerFactory
            .getLogger(AbstractDockerClientTest.class);

    protected DockerClient testClient;

    public void beforeTest() throws DockerException {
        LOG.info("======================= BEFORETEST =======================");
        LOG.info("Connecting to Testing Docker server");
        testClient = new DockerClient();
        LOG.info("Pulling image 'ubuntu'");
        logResponseStream(testClient.pullImageCmd("ubuntu:latest").exec());
        LOG.info("======================= END OF BEFORETEST ================\n\n");
    }

    public void afterTest() {
        LOG.info("======================= END OF AFTERTEST =================");
    }

    public void beforeMethod(Method method) {
        LOG.info(String
                .format("################################## STARTING %s ##################################",
                        method.getName()));
    }

    public void afterMethod(ITestResult result) {
        LOG.info(String
                .format("################################## END OF {} ##################################\n",
                result.getName()));
    }

    protected String logResponseStream(ClientResponse response) {
        String responseString;
        try {
            responseString = DockerClient.asString(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LOG.info("Container log: {}", responseString);
        return responseString;
    }
}
