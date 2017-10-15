package com.miaoxingman.docker.client.core;

import java.util.Set;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.google.common.collect.ImmutableSet;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.LoggingFilter;

public class SelectiveLoggingFilter extends LoggingFilter {
    private static final Set<String> SKIPPED_CONTENT = ImmutableSet.<String>builder()
            .add(MediaType.APPLICATION_OCTET_STREAM)
            .add("application/tar")
            .build();

    @Override
    public ClientResponse handle(ClientRequest request) throws ClientHandlerException {
        Object contentType = request.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
        if (contentType != null && SKIPPED_CONTENT.contains(contentType.toString())) {
            return getNext().handle(request);
        }
        return super.handle(request);
    }
}
