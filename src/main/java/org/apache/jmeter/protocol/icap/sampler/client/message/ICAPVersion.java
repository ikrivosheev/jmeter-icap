package org.apache.jmeter.protocol.icap.sampler.client.message;

import org.apache.jmeter.protocol.icap.sampler.client.AbstractVersion;


public class ICAPVersion extends AbstractVersion {

    public static final ICAPVersion ICAP_1_0 = new ICAPVersion("ICAP/1.0");

    private ICAPVersion(String text) {
        super(text);
    }

    public static ICAPVersion valueOf(String text) throws NullPointerException {
        if (text == null) {
            throw new NullPointerException("text");
        }
        if (text.trim().toUpperCase().equals("ICAP/1.0")) {
            return ICAP_1_0;
        }
        return new ICAPVersion(text);
    }

    @Override
    protected boolean checkProtocolName(String protocolName) {
        return protocolName.equals("ICAP");
    }
}
