package org.apache.jmeter.protocol.icap.sampler.client.message;

import java.net.URI;

public class ICAPResponse extends AbstractICAPMessage {
    private String status;
    private String reason;

    private URI url;

    ICAPResponse(ICAPMethod method, URI url) {
        super(method, ICAPVersion.ICAP_1_0);
        this.url = url;
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

    public String getStartLine() {
        return String.join(" ", (CharSequence) getVersion(), getStatus(), getReason());
    }

    public String toString() {
        String result = super.toString();
        return getStartLine() + "\r\n" + result;
    }
}
