package com.miaoxingman.docker.client;

import static org.apache.commons.io.IOUtils.closeQuietly;

import java.io.Closeable;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.client.apache4.ApacheHttpClient4;
import com.sun.jersey.client.apache4.ApacheHttpClient4Handler;

import com.miaoxingman.docker.client.util.JsonClientFilter;
import com.miaoxingman.docker.client.util.SelectiveLoggingFilter;
import com.miaoxingman.docker.client.Config;
import com.miaoxingman.docker.client.DockerException;
import com.miaoxingman.docker.client.command.CommandFactory;
import com.miaoxingman.docker.client.command.DefaultCommandFactory;
import com.miaoxingman.docker.client.command.ListImagesCmd;
import com.miaoxingman.docker.client.command.PullImageCmd;
import com.miaoxingman.docker.client.command.RemoveImageCmd;
import com.miaoxingman.docker.client.command.VersionCmd;

public class DockerClient implements Closeable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DockerClient.class);
    private Client jerseyClient;
    private final CommandFactory cmdFactory;
    private final WebResource baseResource;

    public DockerClient() {
        this(Config.createDefaultConfigBuilder().build());
    }

    public DockerClient(String serverUrl) {
        this(configWithServerUrl(serverUrl));
    }

    private static Config configWithServerUrl(String serverUrl) {
        return Config.createDefaultConfigBuilder()
                .withUri(serverUrl)
                .build();
    }

    public DockerClient(Config config) {
        this(config, new DefaultCommandFactory());
    }

    public DockerClient(Config config, CommandFactory cmdFactory) {
        this.cmdFactory = cmdFactory;

        HttpClient httpClient = getPoolingHttpClient(config);
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        jerseyClient = new ApacheHttpClient4(new ApacheHttpClient4Handler(httpClient,
                null, false), clientConfig);

        if(config.getReadTimeout() != null) {
            jerseyClient.setReadTimeout(config.getReadTimeout());
        }

        jerseyClient.addFilter(new JsonClientFilter());

        if (config.isLoggingFilterEnabled())
            jerseyClient.addFilter(new SelectiveLoggingFilter());
        
        WebResource webResource = jerseyClient.resource(config.getUri());
        
        if(config.getVersion() != null) {
            baseResource = webResource.path("v" + config.getVersion());
        } else {
            baseResource = webResource;
        }
    }

    private HttpClient getPoolingHttpClient(Config config) {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", config.getUri().getPort(),
                PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory
                .getSocketFactory()));

        PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
        // Increase max total connection
        cm.setMaxTotal(1000);
        // Increase default max connection per route
        cm.setDefaultMaxPerRoute(1000);

        return new DefaultHttpClient(cm);
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

    public static String asString(ClientResponse response) throws IOException {
        StringWriter out = new StringWriter();
        try {
            LineIterator itr = IOUtils.lineIterator(
                    response.getEntityInputStream(), "UTF-8");
            while (itr.hasNext()) {
                String line = itr.next();
                out.write(line + (itr.hasNext() ? "\n" : ""));
            }
        } finally {
            closeQuietly(response.getEntityInputStream());
        }
        return out.toString();
    }
    @Override
    public void close() throws IOException {
        jerseyClient.destroy();
    }
}
