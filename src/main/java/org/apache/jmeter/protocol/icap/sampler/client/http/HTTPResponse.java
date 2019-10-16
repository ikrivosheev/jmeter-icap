package org.apache.jmeter.protocol.icap.sampler.client.http;


import org.apache.jmeter.protocol.icap.sampler.client.HeadersMixin;
import org.apache.jmeter.protocol.icap.sampler.client.Status;

public class HTTPResponse extends HeadersMixin {
    private Status status;
    private HTTPVersion version;

    public HTTPVersion getVersion() {
        return version;
    }

    public void setVersion(HTTPVersion version) {
        this.version = version;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
