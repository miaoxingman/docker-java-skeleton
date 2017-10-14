package com.miaoxingman.docker.client;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.apache4.ApacheHttpClient4;
import com.sun.jersey.client.apache4.ApacheHttpClient4Handler;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import com.miaoxingman.docker.client.model.Image;
import com.miaoxingman.docker.client.util.JsonClientFilter;
import com.miaoxingman.docker.client.Config;
import com.google.common.base.Preconditions;
import com.miaoxingman.docker.client.DockerException;
import com.miaoxingman.docker.client.command.CommandFactory;
import com.miaoxingman.docker.client.command.DefaultCommandFactory;
import com.miaoxingman.docker.client.command.ListImagesCmd;
import com.miaoxingman.docker.client.command.PullImageCmd;
import com.miaoxingman.docker.client.command.RemoveImageCmd;
import com.miaoxingman.docker.client.command.VersionCmd;

public class DockerClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(DockerClient.class);
    private Client jerseyClient;
    private String restEndpointUrl;
    private final CommandFactory cmdFactory = new DefaultCommandFactory();
    private final WebResource baseResource;

    public DockerClient() {
        this(Config.createDefaultConfigBuilder().build());
    }

    public DockerClient(String serverUrl) {
        this(configWithServerUrl(serverUrl));
    }

    public DockerClient(Config config) {
        this(config, new DefaultCommandFactory());
    }

    private static Config configWithServerUrl(String serverUrl) {
        return Config.createDefaultConfigBuilder()
                .withUri(serverUrl)
                .build();
    }
    public DockerClient(Config config, CommandFactory cmdFactory) {
        this.cmdFactory = cmdFactory;
    }
        restEndpointUrl = serverUrl + "/v1.12"; //minimum version supported in my docker server
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        //apache http client setting
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 4243, PlainSocketFactory.getSocketFactory()));

        PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
        cm.setMaxTotal(1000);
        cm.setDefaultMaxPerRoute(1000);
        HttpClient httpClient = new DefaultHttpClient(cm);

        //combine jersey client with apache http client
        jerseyClient = new ApacheHttpClient4(new ApacheHttpClient4Handler(httpClient, null, false), clientConfig);

        jerseyClient.addFilter(new JsonClientFilter());
        jerseyClient.addFilter(new LoggingFilter());
        
        baseResource = jerseyClient.resource(restEndpointUrl);
    }

    public VersionCmd versionCmd() throws DockerException {
        return cmdFactory.versionCmd().WithResource(baseResource);
    }


    public PullImageCmd pullImageCmd(String repository) throws DockerException {
        return cmdFactory.pullImageCmd(repository).WithResource(baseResource);
    }

    public ListImagesCmd listImagesCmd() throws DockerException {
        return cmdFactory.listImagesCmd().WithResource(baseResource);
    }

    public RemoveImageCmd removeImageCmd(String imageId) throws DockerException {
        return cmdFactory.removeImageCmd(imageId).WithResource(baseResource);
    }
}

