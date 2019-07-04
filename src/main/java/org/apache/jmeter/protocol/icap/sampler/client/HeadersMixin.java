package org.apache.jmeter.protocol.icap.sampler.client;

import java.util.*;


public class HeadersMixin {
    private List<Map.Entry<String, String>> headers;


    public HeadersMixin() {
        headers = new LinkedList<>();
    }

    public void addHeader(String name, Object value) {
        Map.Entry<String, String> entry = new AbstractMap.SimpleEntry<>(name, value.toString());
        headers.add(entry);
    }

    public Map.Entry<String, String> getHeader(String name) {
        for (Map.Entry<String, String> value: headers) {
            if (value.getKey().equals(name)) {
                return value;
            }
        }
        return null;
    }

    Set<Map.Entry<String, String>> getHeaders() {
        return new LinkedHashSet<>(headers);
    }

    public boolean containsHeader(String name) {
        return getHeader(name) != null;
    }

    public String toString() {
        ArrayList<String> headers_lines = new ArrayList<>();
        for (Map.Entry<String, String> entry: headers) {
            headers_lines.add(String.format("%s: %s", entry.getKey(), entry.getValue()));
        }
        return String.join("\r\n", headers_lines);
    }
}
