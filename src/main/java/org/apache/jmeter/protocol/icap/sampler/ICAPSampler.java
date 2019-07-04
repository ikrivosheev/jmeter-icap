package org.apache.jmeter.protocol.icap.sampler;

import org.apache.jmeter.protocol.icap.sampler.client.ICAPClient;
import org.apache.jmeter.protocol.icap.sampler.client.ICAPMethod;
import org.apache.jmeter.protocol.icap.sampler.client.ICAPRequest;
import org.apache.jmeter.protocol.icap.sampler.client.ICAPResponse;
import org.apache.jmeter.protocol.icap.sampler.util.ICAPConstatnts;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.jmeter.samplers.*;
import org.apache.jmeter.samplers.Interruptible;
import org.apache.jmeter.config.ConfigTestElement;

import java.io.IOException;
import java.net.URISyntaxException;


public class ICAPSampler extends AbstractSampler implements Interruptible {

    private static Logger logger = LogManager.getLogger(ICAPSampler.class);

    private static final String HOSTNAME = "hostname";
    private static final String PORT = "port";
    private static final String SERVICE = "service";
    private static final String CONNECT_TIMEOUT = "connect_timeout";

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

        ICAPClient client = new ICAPClient(getHost(), getPort());
        ICAPRequest request;
        ICAPResponse response;

        try {
            request = new ICAPRequest(ICAPMethod.OPTIONS, getHost(), getPort(), getService());
            response = client.request(request);
        }
        catch (URISyntaxException exc) {
            result.setSuccessful(false);
            logger.error("Bad URI to connect: " + exc.getMessage());
            return result;
        }
        catch (IOException exc) {
            result.setSuccessful(false);
            logger.error("I/O Error: " + exc.getMessage());
            return result;
        }

        result.setResponseCode(response.getStatus());
        result.setResponseMessage(response.getReason());
        result.setRequestHeaders(request.toString());
        result.setResponseHeaders(response.toString());
        result.sampleEnd();
        result.setSuccessful(true);
        return result;
    }

    public boolean interrupt() {
        return true;
    }
}
