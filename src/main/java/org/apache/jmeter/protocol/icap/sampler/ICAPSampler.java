package org.apache.jmeter.protocol.icap.sampler;

import org.apache.jmeter.protocol.icap.sampler.util.ICAPConstatnts;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.jmeter.samplers.*;
import org.apache.jmeter.samplers.Interruptible;
import org.apache.jmeter.config.ConfigTestElement;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class ICAPSampler extends AbstractSampler implements Interruptible {

    private static Logger logger = LogManager.getLogger(ICAPSampler.class);

    private static final String HOSTNAME = "hostname";
    private static final String PORT = "port";
    private static final String SERVICE = "service";
    private static final String CONNECT_TIMEOUT = "connect_timeout";

    private Socket icapSocket;

    public ICAPSampler() {
        super();
        setName("ICAP Sampler");
    }

    public String getHost() {
        return getPropertyAsString(ICAPSampler.HOSTNAME);
    }

    public int getPort() {
        return getPropertyAsInt(ICAPSampler.PORT, ICAPConstatnts.DEFAULT_PORT);
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

    public int getConnectTimeout() {
        return getPropertyAsInt(ICAPSampler.CONNECT_TIMEOUT, 60);
    }

    public void setConnectTimeout(String connectTimeout) {
        setProperty(ICAPSampler.CONNECT_TIMEOUT, connectTimeout);
    }

    public boolean applies(ConfigTestElement configElement) {
        return true;
    }

    public SampleResult sample(Entry e) {
        SampleResult result = new SampleResult();
        result.setSampleLabel(getName());
        result.sampleStart();
        result.setSuccessful(true);

        OutputStream out;
        String request = "OPTIONS icap://" + getHost() + ":" + getPort() + "/" + getService().trim() + " ICAP/1.0\r\n\r\n";

        try {
            icapSocket = new Socket(getHost(), getPort());
            icapSocket.connect(new InetSocketAddress(getHost(), getPort()), getConnectTimeout());
            out = icapSocket.getOutputStream();
            out.write(request.getBytes());
            icapSocket.close();
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

    public boolean interrupt() {
        try {
            if (icapSocket.isConnected()) {
                icapSocket.close();
            }
            icapSocket = null;
        }
        catch (IOException exc) {
            logger.error("I/O Error: " + exc.getMessage());
        }
        return true;
    }
}
