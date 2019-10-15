package org.apache.jmeter.protocol.icap.sampler.client.message;

import org.apache.jmeter.protocol.icap.sampler.util.ICAPConstatnts;

import java.net.URI;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;


public class ICAPRequest extends AbstractICAPMessage {
    private URI url;
    private ICAPMethod method;
    private int connTimeout;
    private int readTimeout;

    public static final int DEFAULT_CONNECT_TIMEOUT = 5 * 60 * 1000;
    public static final int DEFAULT_READ_TIMEOUT = 5 * 60 * 1000;

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

    public int getConnTimeout() {
        return connTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public InetSocketAddress getSocketAddress() {
        int port = url.getPort();
        if (port == -1) {
            port = ICAPConstatnts.DEFAULT_PORT;
        }
        return new InetSocketAddress(this.url.getHost(), port);
    }

    public ICAPRequest(ICAPMethod method, URI url) {
        super(method, ICAPVersion.ICAP_1_0);
        this.url = url;
        this.method = method;
        this.readTimeout = DEFAULT_READ_TIMEOUT;
        this.connTimeout = DEFAULT_CONNECT_TIMEOUT;
        addHeader("Host", this);
    }

    public ICAPRequest(ICAPMethod method, String host, int port, String service) throws URISyntaxException {
        this(method, new URI("icap", null, host, port, service, null,null));
    }

    @Override
    public Set<Map.Entry<String, String>> getHeaders() {
        Set<Map.Entry<String, String>> headers = super.getHeaders();
        if (!containsHeader("Host")) {
            Map.Entry<String, String> host = new AbstractMap.SimpleEntry<>("Host", this.url.getHost());
            headers.add(host);
        }
        Map.Entry<String, String> encapsulated = new AbstractMap.SimpleEntry<>("Encapsulated", "req-hdr=0");
        headers.add(encapsulated);
        return headers;
    }
}
