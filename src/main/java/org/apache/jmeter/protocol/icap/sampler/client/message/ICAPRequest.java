package org.apache.jmeter.protocol.icap.sampler.client.message;

import org.apache.jmeter.protocol.icap.sampler.util.ICAPConstatnts;

import java.net.URI;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;


public class ICAPRequest extends AbstractICAPMessage {
    private URI uri;

    private ICAPRequestBody body;
    private ICAPMessageElementEnum bodyType;

    public ICAPRequest(ICAPMethod method, URI uri) {
        super(method, ICAPVersion.ICAP_1_0);
        this.uri = uri;
        addHeader("Host", this.uri.getHost());
    }

    public ICAPRequest(ICAPMethod method, String host, int port, String service) throws URISyntaxException {
        this(method, new URI("icap", null, host, port, service, null,null));
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public InetSocketAddress getSocketAddress() {
        int port = uri.getPort();
        if (port == -1) {
            port = ICAPConstatnts.DEFAULT_PORT;
        }
        return new InetSocketAddress(this.uri.getHost(), port);
    }

    public void setHTTPRequestBody(ICAPRequestBody body) {
        this.body = body;
        this.bodyType = ICAPMessageElementEnum.REQBODY;
    }

    public void setHTTPResponseBody(ICAPRequestBody body) {
        this.body = body;
        this.bodyType = ICAPMessageElementEnum.RESBODY;
    }

    public void setICAPOPtionsBody(ICAPRequestBody body) {
        this.body = body;
        this.bodyType = ICAPMessageElementEnum.OPTBODY;
    }

    public ICAPMessageElementEnum getBodyType() {
        return this.bodyType;
    }

    public ICAPRequestBody getBody() {
        return this.body;
    }
}
