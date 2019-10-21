package org.apache.jmeter.protocol.icap.sampler.client.message;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ICAPChunk {
    private boolean last;
    private ByteArrayOutputStream buffer;

    public ICAPChunk(byte[] data, boolean last) throws IOException {
        this.last = last;

        this.buffer = new ByteArrayOutputStream(data.length);
        buffer.write(data);
    }

    public ByteArrayOutputStream getContent() {
        return buffer;
    }

    public int size() {
        return buffer.size();
    }

    public boolean isLast() {
        return last;
    }
}
