package org.apache.jmeter.protocol.icap.sampler.client.message;

import org.apache.jmeter.protocol.icap.sampler.client.Method;


public final class ICAPMethod extends Method {
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

    static {
        METHOD_MAP.put(REQMOD.toString(), REQMOD);
        METHOD_MAP.put(RESPMOD.toString(), RESPMOD);
        METHOD_MAP.put(OPTIONS.toString(), OPTIONS);
    }

    private ICAPMethod(String name) {
        super(name);
    }

    public static ICAPMethod valueOf(String name) {
        return (ICAPMethod) Method.valueOf(name);
    }
}
