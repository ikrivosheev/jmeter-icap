package org.apache.jmeter.protocol.icap.sampler.client;

import java.net.Socket;
import java.net.URI;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;


public class ICAPClient {
    private Socket socket;
    private InetSocketAddress address;
    private int connTimeout;
    private int readTimeout;

    public static final int DEFAULT_CONNECT_TIMEOUT = 5 * 60 * 1000;
    public static final int DEFAULT_READ_TIMEOUT = 5 * 60 * 1000;

    public ICAPClient(String host, int port) {
        this(host, port, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    public ICAPClient(InetSocketAddress address) {
        this(address, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    public ICAPClient(String host, int port, int connectTimeout, int readTimeout) {
        this(new InetSocketAddress(host, port), connectTimeout, readTimeout);
    }

    public ICAPClient(InetSocketAddress address, int connectTimeout, int readTimeout) {
        this.address = address;
        this.connTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.socket = new Socket();
    }

    public ICAPResponse request(ICAPMethod method, String service) throws IOException, URISyntaxException {
        URI url = new URI("icap", null, address.getHostString(), address.getPort(), service, null,null);
        return request(new ICAPRequest(method, url));
    }

    public ICAPResponse request(ICAPRequest req) throws IOException {
        socket.connect(address, connTimeout);
        socket.setSoTimeout(readTimeout);
        ICAPProtocol.write(socket, req);
        ICAPResponse response = ICAPProtocol.read(socket, req);
        socket.close();
        return response;
    }

    public ICAPResponse options(String service) throws IOException, URISyntaxException {
        return request(ICAPMethod.OPTIONS, service);
    }

    public ICAPResponse reqmod(String service) throws IOException, URISyntaxException {
        return request(ICAPMethod.REQMOD, service);
    }

    public ICAPResponse respmod(String service) throws IOException, URISyntaxException {
        return request(ICAPMethod.RESPMOD, service);
    }

    public void close() throws IOException {
        if (socket.isConnected()) {
            socket.close();
        }
    }
}
