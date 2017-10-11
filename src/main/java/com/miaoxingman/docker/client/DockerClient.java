package com.miaoxingman.docker.client;

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
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.apache4.ApacheHttpClient4;
import com.sun.jersey.client.apache4.ApacheHttpClient4Handler;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.miaoxingman.docker.client.model.Version;
import com.miaoxingman.docker.client.util.JsonClientFilter;
import com.google.common.base.Preconditions;
import com.miaoxingman.docker.client.DockerException;

public class DockerClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(DockerClient.class);
    private Client jerseyClient;
    private String restEndpointUrl;

    public DockerClient(String serverUrl) {
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
    }

    public Version version() throws DockerException {
        WebResource webResource = jerseyClient.resource(restEndpointUrl + "/version");
        try {
            LOGGER.debug("get {}", webResource);
            return webResource.accept(MediaType.APPLICATION_JSON).get(Version.class);
        } catch (UniformInterfaceException exception) {
            throw new DockerException(exception);
        }
    }

    public ClientResponse pull(String repository) throws DockerException {
        return this.pull(repository, null, null);
    }

    public ClientResponse pull(String repository, String tag) throws DockerException {
        return this.pull(repository, tag, null);
    }

    public ClientResponse pull(String repository, String tag, String registry)
            throws DockerException {
        Preconditions.checkNotNull(repository, "Repository was not specified");

        if(StringUtils.countMatches(repository, ":") == 1) {
            String repositoryTag[] = StringUtils.split(repository);
            repository = repositoryTag[0];
            tag = repositoryTag[1];
        }

        MultivaluedMap<String,String> params = new MultivaluedMapImpl();
        params.add("tag", tag);
        params.add("fromImage", repository);
        params.add("registry", registry);

        WebResource webResource = jerseyClient.
                resource(restEndpointUrl + "/images/create").
                queryParams(params);

        try {
            LOGGER.trace("POST: {}", webResource);
            return webResource.accept(MediaType.APPLICATION_OCTET_STREAM_TYPE).post(ClientResponse.class);
        } catch (UniformInterfaceException e) {
            throw new DockerException(e);
        }
    }
}
