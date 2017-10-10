package com.miaoxingman.docker.client.test;

import static org.testng.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.miaoxingman.docker.client.DockerException;
import com.miaoxingman.docker.client.model.Version;
import com.miaoxingman.docker.client.DockerClient;

public class DockerClientTest extends Assert {

    public static final Logger LOG = LoggerFactory.getLogger(DockerClientTest.class);
    private DockerClient dockerClient;

    @BeforeTest
    public void beforeTest() {
        LOG.info("======================= BEFORETEST =======================");
        String url = System.getProperty("docker.url", "http://10.117.162.153:2375");
        LOG.info("Connecting to Docker server at " + url);
        dockerClient = new DockerClient(url);
    }

    @AfterTest
    public void afterTest() {
        LOG.info("======================= END OF AFTERTEST =======================");
    }

    @Test
    public void testDockerVersion() throws DockerException {
        Version version = dockerClient.version();
        LOG.info(version.toString());

        assertTrue(version.getGoVersion().length() > 0);
        assertTrue(version.getVersion().length() > 0);

        assertEquals(StringUtils.split(version.getVersion(), ".").length, 3);
    }
}
