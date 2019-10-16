package org.apache.jmeter.protocol.icap.sampler.client.message;

import org.apache.jmeter.protocol.icap.sampler.client.HeadersMixin;
import org.apache.jmeter.protocol.icap.sampler.client.http.HTTPRequest;
import org.apache.jmeter.protocol.icap.sampler.client.http.HTTPResponse;


public abstract class AbstractICAPMessage extends HeadersMixin {
    private ICAPMethod method;
    private ICAPVersion version;

    private HTTPRequest httpRequest;
    private HTTPResponse httpResponse;

    public AbstractICAPMessage(ICAPMethod method, ICAPVersion version) {
        this.method = method;
        this.version = version;
    }

    public ICAPMethod getMethod() {
        return method;
    }

    public void setMethod(ICAPMethod method) {
        this.method = method;
    }

    public ICAPVersion getVersion() {
        return version;
    }

    public void setVersion(ICAPVersion version) {
        this.version = version;
    }

    public HTTPRequest getHttpRequest() {
        return httpRequest;
    }

    public void setHttpRequest(HTTPRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public HTTPResponse getHttpResponse() {
        return httpResponse;
    }

    public void setHttpResponse(HTTPResponse httpResponse) {
        this.httpResponse = httpResponse;
    }
}
