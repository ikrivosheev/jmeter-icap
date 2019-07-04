package org.apache.jmeter.protocol.icap.sampler.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;


public class ICAPProtocol {
    public static void write(Socket socket, ICAPRequest request) throws IOException {
        OutputStream out = socket.getOutputStream();

        ArrayList<String> headers_lines = new ArrayList<>();
        headers_lines.add(request.getStartLine());
        for (Map.Entry<String, String> entry: request.getHeaders()) {
            headers_lines.add(String.format("%s: %s", entry.getKey(), entry.getValue()));
        }
        headers_lines.add("\r\n");
        out.write(String.join("\r\n", headers_lines).getBytes(StandardCharsets.ISO_8859_1));
    }

    public static ICAPResponse read(Socket socket, ICAPRequest request) throws IOException {
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

        return response;
    }
}
