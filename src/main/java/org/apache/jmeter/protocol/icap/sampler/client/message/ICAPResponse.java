package org.apache.jmeter.protocol.icap.sampler.client.message;

import java.net.URI;

public class ICAPResponse extends AbstractICAPMessage {
    private String status;
    private String reason;

    private URI uri;

    public ICAPResponse(ICAPMethod method, URI uri) {
        super(method, ICAPVersion.ICAP_1_0);
        this.uri = uri;
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

    public URI getUri() {
        return uri;
    }

    public String getStartLine() {
        return String.join(" ", (CharSequence) getVersion(), getStatus(), getReason());
    }

    public String toString() {
        String result = super.toString();
        return getStartLine() + "\r\n" + result;
    }
}
