package org.apache.jmeter.protocol.icap.sampler.client.http;

import org.apache.jmeter.protocol.icap.sampler.client.HeadersMixin;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


public class HTTPRequest extends HeadersMixin {
    private URL url;
    private HTTPMethod method;
    private HTTPVersion version;

    public HTTPRequest(HTTPMethod method, URL url, HTTPVersion version) {
        this.method = method;
        this.url = url;
        this.version = version;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public URI getUri() throws URISyntaxException {
        return url.toURI();
    }

    public HTTPMethod getMethod() {
        return method;
    }

    public void setMethod(HTTPMethod method) {
        this.method = method;
    }

    public HTTPVersion getVersion() {
        return version;
    }

    public void setVersion(HTTPVersion version) {
        this.version = version;
    }
}
