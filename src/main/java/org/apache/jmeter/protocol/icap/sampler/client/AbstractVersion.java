package org.apache.jmeter.protocol.icap.sampler.client;

import org.apache.jmeter.protocol.icap.sampler.client.message.ICAPVersion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractVersion implements Version {
    private String protocolName;
    private int major;
    private int minor;
    private String text;

    private static final Pattern VERSION_PATTERN = Pattern.compile("(\\S+)/(\\d+)\\.(\\d+)");

    protected AbstractVersion(String text) {
        if(text == null) {
            throw new NullPointerException("text");
        }
        Matcher m = VERSION_PATTERN.matcher(text.trim().toUpperCase());
        if (!m.matches()) {
            throw new IllegalArgumentException("invalid version format: [" + text + "]");
        }

        protocolName = m.group(1);

        if (!checkProtocolName(protocolName)) {
            throw new IllegalArgumentException("invalid version format: [" + text + "]");
        }
        major = Integer.parseInt(m.group(2));
        minor = Integer.parseInt(m.group(3));
        this.text = text;
    }

    protected abstract boolean checkProtocolName(String text);

    @Override
    public String getProtocolName() {
        return protocolName;
    }

    @Override
    public int getMajor() {
        return major;
    }

    @Override
    public int getMinor() {
        return minor;
    }

    @Override
    public String toString() {
        return text;
    }
}
