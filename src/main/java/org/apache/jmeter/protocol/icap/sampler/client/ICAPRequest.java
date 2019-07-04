package org.apache.jmeter.protocol.icap.sampler.client;

import java.net.URI;
import java.net.URISyntaxException;


public class ICAPRequest extends HeadersMixin {
    private URI url;
    private ICAPMethod method;
    private String version;

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public ICAPMethod getMethod() {
        return method;
    }

    public void setMethod(ICAPMethod method) {
        this.method = method;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStartLine() {
        return String.join(" ", getMethod().toString(), getUrl().toString(), getVersion());
    }

    public ICAPRequest(ICAPMethod method, URI url) {
        super();
        this.url = url;
        this.method = method;
        this.version = ICAPVersion.ICAP_1_0;
    }

    public ICAPRequest(ICAPMethod method, String host, int port, String service) throws URISyntaxException {
        this(method, new URI("icap", null, host, port, service, null,null));
    }

    public String toString() {
        String result = super.toString();
        return getStartLine() + "\r\n" + result;
    }
}
