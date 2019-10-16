package org.apache.jmeter.protocol.icap.sampler.client;

import java.util.HashMap;
import java.util.Map;

public class Method {
    private String name;
    protected static final Map<String, Method> METHOD_MAP = new HashMap<>();

    protected Method(String name) {
        this.name = name;
    }

    public static Method valueOf(String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }

        name = name.trim().toUpperCase();
        if (name.length() == 0) {
            throw new IllegalArgumentException("empty name");
        }

        Method result = METHOD_MAP.get(name);
        if (result != null) {
            return result;
        } else {
            return new Method(name);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
