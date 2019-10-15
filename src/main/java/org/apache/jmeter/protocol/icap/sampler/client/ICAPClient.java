package org.apache.jmeter.protocol.icap.sampler.client;

import org.apache.jmeter.protocol.icap.sampler.client.message.ICAPMethod;
import org.apache.jmeter.protocol.icap.sampler.client.message.ICAPRequest;
import org.apache.jmeter.protocol.icap.sampler.client.message.ICAPResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;


public class ICAPClient {

    public ICAPResponse request(ICAPMethod method, String host, int port, String service) throws IOException, URISyntaxException {
        URI url = new URI("icap", null, host, port, service, null,null);
        return request(new ICAPRequest(method, url));
    }

    public ICAPResponse request(ICAPRequest req) throws IOException {
        return  _request(req);
    }

    private ICAPResponse _request(ICAPRequest request) throws IOException {
        Socket socket = new Socket();
        socket.connect(request.getSocketAddress(), request.getConnTimeout());
        socket.setSoTimeout(request.getReadTimeout());

        OutputStream out = socket.getOutputStream();

        ArrayList<String> headers_lines = new ArrayList<>();
        headers_lines.add(request.getStartLine());
        for (Map.Entry<String, String> entry: request.getHeaders()) {
            headers_lines.add(String.format("%s: %s", entry.getKey(), entry.getValue()));
        }
        headers_lines.add(ICAPRequest.NEWLINE);
        out.write(String.join(ICAPRequest.NEWLINE, headers_lines).getBytes(StandardCharsets.ISO_8859_1));

        ICAPResponse response = new ICAPResponse(request.getMethod(), request.getUrl());
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = in.readLine();
        String[] startLine = line.split(" ");
        response.setVersion(startLine[0]);
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
