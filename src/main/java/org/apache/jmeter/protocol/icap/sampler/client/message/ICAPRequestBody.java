package org.apache.jmeter.protocol.icap.sampler.client.message;

public interface ICAPRequestBody {
    boolean isEOF();
    void close() throws Exception;
    ICAPChunk readChunk() throws Exception;
}
