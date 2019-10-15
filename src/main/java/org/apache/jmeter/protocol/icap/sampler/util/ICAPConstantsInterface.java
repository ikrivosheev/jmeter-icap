package org.apache.jmeter.protocol.icap.sampler.util;


import org.apache.jmeter.protocol.icap.sampler.client.message.ICAPMethod;

public interface ICAPConstantsInterface {
    int DEFAULT_PORT = 1344;

    String[] METHODS = {ICAPMethod.OPTIONS.toString(), ICAPMethod.REQMOD.toString(), ICAPMethod.RESPMOD.toString()};

}
