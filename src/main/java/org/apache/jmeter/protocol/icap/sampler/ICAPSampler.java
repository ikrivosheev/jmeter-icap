package org.apache.jmeter.protocol.icap.sampler;

import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.jmeter.samplers.*;
import org.apache.jmeter.config.ConfigTestElement;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class ICAPSampler extends AbstractSampler {

    private static Logger logger = LogManager.getLogger(ICAPSampler.class);

    private static final String HOSTNAME = "hostname";
    private static final String PORT = "port";
    private static final String SERVICE = "service";

    public ICAPSampler() {
        super();
        setName("ICAP Sampler");
    }

    public String getHost() {
        return getPropertyAsString(ICAPSampler.HOSTNAME);
    }

    public int getPort() {
        return getPropertyAsInt(ICAPSampler.PORT, 1344);
    }

    public String getService() {
        return getPropertyAsString(ICAPSampler.SERVICE);
    }

    public void setHost(String host) {
        setProperty(ICAPSampler.HOSTNAME, host);
    }

    public void setPort(String port) {
        setProperty(ICAPSampler.PORT, port);
    }

    public void setService(String service) {
        setProperty(ICAPSampler.SERVICE, service);
    }

    public boolean applies(ConfigTestElement configElement) {
        return true;
    }

    public SampleResult sample(Entry e) {
        SampleResult result = new SampleResult();
        result.setSampleLabel(getName());
        result.sampleStart();
        result.setSuccessful(true);

        Socket icap_socket;
        OutputStream out;
        String request = "OPTIONS icap://" + getHost() + ":" + getPort() + "/" + getService().trim() + " ICAP/1.0\r\n\r\n";

        try {
            icap_socket = new Socket(getHost(), getPort());
            out = icap_socket.getOutputStream();
            out.write(request.getBytes());
            icap_socket.close();
        }
        catch (UnknownHostException exc) {
            result.setSuccessful(false);
            logger.error("Can not connect to host: " + exc.getMessage());
        }
        catch (IOException exc) {
            result.setSuccessful(false);
            logger.error("I/O Error: " + exc.getMessage());
        }

        result.sampleEnd();
        return result;
    }
}
