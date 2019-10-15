package org.apache.jmeter.protocol.icap.sampler.client.message;

import java.util.Map;
import java.util.HashMap;


public final class ICAPMethod {
    /**
     * Request Modification
     */
    public static final ICAPMethod REQMOD = new ICAPMethod("REQMOD");

    /**
     * Response Modification
     */
    public static final ICAPMethod RESPMOD = new ICAPMethod("RESPMOD");

    /**
     * learn about configuration
     */
    public static final ICAPMethod OPTIONS = new ICAPMethod("OPTIONS");

    private static final Map<String, ICAPMethod> METHOD_MAP = new HashMap<>();

    static {
        METHOD_MAP.put(REQMOD.toString(), REQMOD);
        METHOD_MAP.put(RESPMOD.toString(), RESPMOD);
        METHOD_MAP.put(OPTIONS.toString(), OPTIONS);
    }

    private String name;

    private ICAPMethod(String name) {
        this.name = name;
    }

    public static ICAPMethod valueOf(String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }

        name = name.trim().toUpperCase();
        if (name.length() == 0) {
            throw new IllegalArgumentException("empty name");
        }

        ICAPMethod result = METHOD_MAP.get(name);
        if (result != null) {
            return result;
        } else {
            return new ICAPMethod(name);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
