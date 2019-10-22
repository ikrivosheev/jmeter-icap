package org.apache.jmeter.protocol.icap.sampler.client;

import org.apache.jmeter.protocol.icap.sampler.client.codecs.ICAPMessageEncoder;
import org.apache.jmeter.protocol.icap.sampler.client.message.ICAPMethod;
import org.apache.jmeter.protocol.icap.sampler.client.message.ICAPRequest;
import org.apache.jmeter.protocol.icap.sampler.client.message.ICAPResponse;
import org.apache.jmeter.protocol.icap.sampler.client.message.ICAPVersion;

import java.io.*;
import java.net.Socket;
import java.net.URI;


public class ICAPClient {
    private int connTimeout;
    private int readTimeout;

    public static final int DEFAULT_CONNECT_TIMEOUT = 5 * 60 * 1000;
    public static final int DEFAULT_READ_TIMEOUT = 5 * 60 * 1000;

    public ICAPClient() {
        this.readTimeout = DEFAULT_READ_TIMEOUT;
        this.connTimeout = DEFAULT_CONNECT_TIMEOUT;
    }

    public ICAPClient(int connTimeout) {
        this.readTimeout = connTimeout;
        this.connTimeout = DEFAULT_CONNECT_TIMEOUT;
    }

    public ICAPClient(int connTimeout, int readTimeout) {
        this.readTimeout = connTimeout;
        this.connTimeout = readTimeout;
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


    public ICAPResponse request(ICAPMethod method, String host, int port, String service) throws Exception {
        URI url = new URI("icap", null, host, port, service, null,null);
        return request(new ICAPRequest(method, url));
    }

    public ICAPResponse request(ICAPRequest request) throws Exception {
        Socket socket = new Socket();
        ICAPMessageEncoder encoder = new ICAPMessageEncoder();

        socket.connect(request.getSocketAddress(), connTimeout);
        socket.setSoTimeout(readTimeout);

        OutputStream out = new BufferedOutputStream(socket.getOutputStream());
        encoder.encode(out, request);

        ICAPResponse response = new ICAPResponse(request.getMethod(), request.getUri());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = in.readLine();
        String[] startLine = line.split(" ");
        response.setVersion(ICAPVersion.valueOf(startLine[0]));
        response.setStatus(startLine[1]);
        response.setReason(startLine[2]);

        line = in.readLine();
        while (!line.isEmpty()) {
            String[] header = line.split(": ");
            response.addHeader(header[0], header[1]);
            line = in.readLine();
        }

        socket.close();
        return response;
    }
}
