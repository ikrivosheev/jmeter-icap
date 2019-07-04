package org.apache.jmeter.protocol.icap.sampler.client;

import java.net.URI;


public class ICAPResponse extends HeadersMixin {
    private String version;
    private String status;
    private String reason;

    private URI url;
    private ICAPMethod method;

    ICAPResponse(ICAPMethod method, URI url) {
        super();

        this.url = url;
        this.method = method;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public URI getUrl() {
        return url;
    }

    public ICAPMethod getMethod() {
        return method;
    }

}
