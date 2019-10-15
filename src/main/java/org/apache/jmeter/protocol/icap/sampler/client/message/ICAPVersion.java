package org.apache.jmeter.protocol.icap.sampler.client.message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ICAPVersion {

    public static final ICAPVersion ICAP_1_0 = new ICAPVersion("ICAP", 1, 0);
    private static final Pattern VERSION_PATTERN = Pattern.compile("(\\S+)/(\\d+)\\.(\\d+)");

    private String protocolName;
    private int major;
    private int minor;
    private String text;

    private ICAPVersion(String protocolName, int major, int minor) {
        this.protocolName = protocolName;
        this.major = major;
        this.minor = minor;
        this.text = protocolName + '/' + major + '.' + minor;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public static ICAPVersion valueOf(String version) throws NullPointerException {
        if(version == null) {
            throw new NullPointerException(version);
        }

        version = version.trim().toUpperCase();
        if (version.equals(ICAP_1_0.toString())) {
            return ICAP_1_0;
        }

        Matcher m = VERSION_PATTERN.matcher(version);
        if (!m.matches() ) {
            throw new IllegalArgumentException("invalid version format: [" + version + "]");
        }
        String protocolName = m.group(1);
        int major = Integer.parseInt(m.group(2));
        int minor = Integer.parseInt(m.group(3));
        return new ICAPVersion(protocolName, major, minor);
    }

    @Override
    public String toString() {
        return this.text;
    }
}
