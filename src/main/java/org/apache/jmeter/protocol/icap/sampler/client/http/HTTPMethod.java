package org.apache.jmeter.protocol.icap.sampler.client.http;

import org.apache.jmeter.protocol.icap.sampler.client.Method;


public final class HTTPMethod extends Method {
    public static final HTTPMethod HEAD = new HTTPMethod("HEAD");
    public static final HTTPMethod GET = new HTTPMethod("GET");
    public static final HTTPMethod PUT = new HTTPMethod("PUT");
    public static final HTTPMethod POST = new HTTPMethod("POST");
    public static final HTTPMethod PATCH = new HTTPMethod("PATCH");
    public static final HTTPMethod DELETE = new HTTPMethod("DELETE");

    static {
        METHOD_MAP.put(HEAD.toString(), HEAD);
        METHOD_MAP.put(GET.toString(), GET);
        METHOD_MAP.put(PUT.toString(), PUT);
        METHOD_MAP.put(POST.toString(), POST);
        METHOD_MAP.put(PATCH.toString(), PATCH);
        METHOD_MAP.put(DELETE.toString(), DELETE);
    }

    private HTTPMethod(String name) {
            super(name);
        }

    public static HTTPMethod valueOf(String name) {
        return (HTTPMethod) Method.valueOf(name);
    }
}
