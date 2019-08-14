package org.apache.jmeter.protocol.icap.sampler.client;

public class ICAPBytesBody implements IICAPBody {
    private byte[] body;

    ICAPBytesBody(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
