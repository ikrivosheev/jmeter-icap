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

    private static final String METHOD = "method";
    private static final String HOSTNAME = "hostname";
    private static final String PORT = "port";
    private static final String SERVICE = "service";
    private static final String CONNECT_TIMEOUT = "connect_timeout";
    private static final String READ_TIMEOUT = "read_timeout";
    private static final String BODY_FILE = "body_file";

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

    public int getPort() {
        return getPropertyAsInt(ICAPSampler.PORT, ICAPConstatnts.DEFAULT_PORT);
    }

    public void setPort(int port) {
        setProperty(ICAPSampler.PORT, port);
    }

    public String getService() {
        return getPropertyAsString(ICAPSampler.SERVICE);
    }

    public void setService(String service) {
        setProperty(ICAPSampler.SERVICE, service);
    }

    public int getConnectTimeout() {
        return getPropertyAsInt(ICAPSampler.CONNECT_TIMEOUT, 60);
    }

    public void setConnectTimeout(int connectTimeout) {
        setProperty(ICAPSampler.CONNECT_TIMEOUT, connectTimeout);
    }

    public int getReadTimeout() {
        return getPropertyAsInt(ICAPSampler.READ_TIMEOUT, 60);
    }

    public void setReadTimeout(int readTimeout) {
        setProperty(ICAPSampler.READ_TIMEOUT, readTimeout);
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
            ICAPClient client = new ICAPClient(getConnectTimeout(), getReadTimeout());
            request = new ICAPRequest(ICAPMethod.valueOf(getMethod()), getHost(), getPort(), getService());

            ICAPRequestBody body = new ICAPRequestBodyStream(new FileInputStream(getBodyFile()));
            request.setBody(body, ICAPMessageElementEnum.REQBODY);
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
