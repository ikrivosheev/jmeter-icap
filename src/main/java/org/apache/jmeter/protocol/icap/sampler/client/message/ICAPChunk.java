package org.apache.jmeter.protocol.icap.sampler.client.message;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ICAPChunk {
    private boolean last;
    private ByteArrayOutputStream buffer;

    public ICAPChunk(byte[] data, int size) {
        this.last = size == 0;

        this.buffer = new ByteArrayOutputStream(size);
        buffer.write(data, 0, size);
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
