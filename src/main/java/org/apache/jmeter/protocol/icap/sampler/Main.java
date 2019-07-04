package org.apache.jmeter.protocol.icap.sampler;

import org.apache.jmeter.protocol.icap.sampler.client.ICAPClient;
import org.apache.jmeter.protocol.icap.sampler.client.ICAPResponse;


public class Main {
    public static void main(String[] args) {
        try {
            ICAPClient client = new ICAPClient("localhost", 1344);
            ICAPResponse response = client.options("/bypass");
            System.out.println("OK!!");
        }
        catch (Exception exc) {
            System.err.println("ERROR: " + exc.getMessage());
        }

    }
}
