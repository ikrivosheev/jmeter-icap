package org.apache.jmeter.protocol.icap.sampler;

import org.apache.jmeter.protocol.icap.sampler.client.ICAPClient;
import org.apache.jmeter.protocol.icap.sampler.client.message.*;
import org.apache.jmeter.protocol.icap.sampler.util.ICAPConstatnts;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.jmeter.samplers.*;
import org.apache.jmeter.samplers.Interruptible;
import org.apache.jmeter.config.ConfigTestElement;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;


public class ICAPSampler extends AbstractSampler implements Interruptible {

    private static Logger logger = LogManager.getLogger(ICAPSampler.class);

    private static final String METHOD = "ICAP.method";
    private static final String HOSTNAME = "ICAP.hostname";
    private static final String PORT = "ICAP.port";
    private static final String SERVICE = "ICAP.service";
    private static final String CONNECT_TIMEOUT = "ICAP.connect_timeout";
    private static final String READ_TIMEOUT = "ICAP.read_timeout";
    private static final String BODY_FILE = "ICAP.body_file";

    private static String DEFAULT_CONNECT_TIMEOUT = "60";
    private static String DEFAULT_READ_TIMEOUT = "60";

    public ICAPSampler() {
        super();
        setName("ICAP Sampler");
    }

    public String getMethod() {
        return getPropertyAsString(ICAPSampler.METHOD);
    }

    public void setMethod(String method) {
        setProperty(ICAPSampler.METHOD, method);
    }

    public String getHost() {
        return getPropertyAsString(ICAPSampler.HOSTNAME);
    }

    public void setHost(String host) {
        setProperty(ICAPSampler.HOSTNAME, host);
    }

    public String getPort() {
        return getPropertyAsString(ICAPSampler.PORT, String.valueOf(ICAPConstatnts.DEFAULT_PORT));
    }

    public void setPort(String port) {
        setProperty(ICAPSampler.PORT, port);
    }

    public String getService() {
        return getPropertyAsString(ICAPSampler.SERVICE);
    }

    public void setService(String service) {
        setProperty(ICAPSampler.SERVICE, service);
    }

    public String getConnectTimeout() {
        return getPropertyAsString(ICAPSampler.CONNECT_TIMEOUT, DEFAULT_CONNECT_TIMEOUT);
    }

    public void setConnectTimeout(String connectTimeout) {
        setProperty(ICAPSampler.CONNECT_TIMEOUT, connectTimeout, DEFAULT_CONNECT_TIMEOUT);
    }

    public String getReadTimeout() {
        return getPropertyAsString(ICAPSampler.READ_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    public void setReadTimeout(String readTimeout) {
        setProperty(ICAPSampler.READ_TIMEOUT, readTimeout, DEFAULT_READ_TIMEOUT);
    }

    public String getBodyFile() {
        return getPropertyAsString(BODY_FILE);
    }

    public void setBodyFile(String filename) {
        setProperty(BODY_FILE, filename);
    }

    public boolean applies(ConfigTestElement configElement) {
        return true;
    }

    public SampleResult sample(Entry e) {
        ICAPRequest request;
        ICAPResponse response;

        SampleResult result = new SampleResult();
        result.setSampleLabel(getName());
        result.sampleStart();

        try {

            ICAPClient client = new ICAPClient(getPropertyAsInt(CONNECT_TIMEOUT), getPropertyAsInt(READ_TIMEOUT));
            request = new ICAPRequest(ICAPMethod.valueOf(getMethod()), getHost(), getPropertyAsInt(PORT), getService());

            ICAPRequestBody body = new ICAPRequestBodyStream(new FileInputStream(getBodyFile()));

            if (!getBodyFile().equals("")) {
                request.setBody(body, ICAPMessageElementEnum.RESBODY);
            }
            logger.info("Create ICAP request ");

            response = client.request(request);
            logger.info("Send ICAP request to " + request.getSocketAddress());
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
        catch (Exception exc) {
            result.setSuccessful(false);
            logger.error("Error: " + exc.getMessage());
            return result;
        }

        result.setRequestHeaders(request.toString());
        result.setResponseHeaders(response.toString());

        result.setResponseCode(response.getStatus());
        result.setResponseMessage(response.getReason());
        result.setSuccessful(true);

        result.sampleEnd();
        return result;
    }

    public boolean interrupt() {
        return true;
    }
}
