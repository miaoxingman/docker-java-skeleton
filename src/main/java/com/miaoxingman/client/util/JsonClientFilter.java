package com.miaoxingman.client.util;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;

public class JsonClientFilter extends ClientFilter {
    public ClientResponse handle(ClientRequest cr) {
        MediaType contentType = (MediaType) cr.getHeaders().getFirst("Content-Type");
        if (contentType != null && contentType.getParameters().containsKey("charset")) {
            Map<String, String> contentMap = new HashMap<String, String>();
            contentMap.put("charset", "UTF-8");
            cr.getHeaders().putSingle("Content-Type", new MediaType(contentType.getType(), contentType.getSubtype(), contentMap));
        }

        // Call the next filter
        ClientResponse resp = getNext().handle(cr);

        String respContentType = resp.getHeaders().getFirst("Content-Type");
        if (respContentType.startsWith("text/plain")) {
            String newContentType = "application/json" + respContentType.substring(10);
            resp.getHeaders().putSingle("Content-Type", newContentType);
        }
        return resp;
    }
}
