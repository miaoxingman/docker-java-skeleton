package com.miaoxingman.docker.client.test;

import static org.testng.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.miaoxingman.docker.client.api.DockerException;
import com.miaoxingman.docker.client.api.model.Image;
import com.miaoxingman.docker.client.api.model.Version;

public class DockerClientTest extends AbstractDockerClientTest {

    public static final Logger LOG = LoggerFactory.getLogger(DockerClientTest.class);

    @BeforeTest
    public void beforeTest() throws DockerException {
        super.beforeTest();
    }
    @AfterTest
    public void afterTest() {
        super.afterTest();
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        super.beforeMethod(method);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        super.afterMethod(result);
    }

    @Test
    public void testDockerVersion() throws DockerException {
        Version version = testClient.versionCmd().exec();
        LOG.info("\n**********************************\n");
        LOG.info(version.toString());
        LOG.info("\n**********************************\n");
        assertTrue(version.getGoVersion().length() > 0);
        assertTrue(version.getVersion().length() > 0);

        assertEquals(StringUtils.split(version.getVersion(), ".").length, 3);
    }

/*
    @Test
    public void testDockerListImages() throws DockerException {
        List<Image> images = testClient.listImagesCmd().exec();
        LOG.info(images.toString());
        assertTrue(images.size() != 0);
    }
*/
}
