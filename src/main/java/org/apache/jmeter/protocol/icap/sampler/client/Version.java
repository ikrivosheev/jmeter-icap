package org.apache.jmeter.protocol.icap.sampler.client;

public interface Version {
    String getProtocolName();

    int getMajor();

    int getMinor();

    String toString();
}
