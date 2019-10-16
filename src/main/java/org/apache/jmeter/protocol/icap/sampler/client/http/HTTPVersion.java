package org.apache.jmeter.protocol.icap.sampler.client.http;

import org.apache.jmeter.protocol.icap.sampler.client.AbstractVersion;


public class HTTPVersion extends AbstractVersion {

    public static final HTTPVersion HTTP_0_9 = new HTTPVersion("HTTP/0.9");
    public static final HTTPVersion HTTP_1_0 = new HTTPVersion("HTTP/1.0");
    public static final HTTPVersion HTTP_1_1 = new HTTPVersion("HTTP/1.1");

    private HTTPVersion(String text) {
        super(text);
    }

    public static HTTPVersion valueOf(String text) throws NullPointerException {
        if (text == null) {
            throw new NullPointerException("text");
        }
        if (text.trim().toUpperCase().equals("HTTP/1.0")) {
            return HTTP_1_0;
        }
        if (text.trim().toUpperCase().equals("HTTP/1.1")) {
            return HTTP_1_1;
        }
        return new HTTPVersion(text);
    }

    @Override
    protected boolean checkProtocolName(String protocolName) {
        return protocolName.equals("HTTP");
    }
}
