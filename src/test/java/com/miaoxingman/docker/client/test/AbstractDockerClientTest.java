package com.miaoxingman.docker.client.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Method;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestResult;

import com.miaoxingman.docker.client.api.DockerClient;
import com.miaoxingman.docker.client.api.DockerException;
import com.miaoxingman.docker.client.core.DockerClientImpl;
import com.sun.jersey.api.client.ClientResponse;

public class AbstractDockerClientTest extends Assert {

    public static final Logger LOG = LoggerFactory
            .getLogger(AbstractDockerClientTest.class);

    protected DockerClient testClient;

    public void beforeTest() throws DockerException {
        LOG.info("======================= BEFORETEST =======================");
        LOG.info("Connecting to Testing Docker server");
        testClient = new DockerClientImpl();
        LOG.info("Pulling image 'ubuntu'");
        //asString(testClient.pullImageCmd("ubuntu:latest").exec());
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

    protected String asString(InputStream response)  {
        
        StringWriter logwriter = new StringWriter();
        
        try {
            LineIterator itr = IOUtils.lineIterator(
                    response, "UTF-8");

            while (itr.hasNext()) {
                String line = itr.next();
                logwriter.write(line + (itr.hasNext() ? "\n" : ""));
                //LOG.info("line: "+line);
            }
            
            return logwriter.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(response);
        }
    }
}
