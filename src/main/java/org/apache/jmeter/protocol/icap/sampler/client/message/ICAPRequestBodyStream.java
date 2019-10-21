package org.apache.jmeter.protocol.icap.sampler.client.message;

import java.io.InputStream;


public class ICAPRequestBodyStream implements ICAPRequestBody {
    private static int DEFAULT_CHUNK_SIZE = 8 * 1024;

    private InputStream reader;
    private byte[] buffer;
    private boolean eof;

    public ICAPRequestBodyStream(InputStream reader) {
        eof = false;
        this.reader = reader;
        buffer = new byte[DEFAULT_CHUNK_SIZE];
    }

    @Override
    public boolean isEOF() {
        return eof;
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }

    @Override
    public ICAPChunk readChunk() throws Exception {
        if (eof) {
            return new ICAPChunk(new byte[0], true);
        }
        ICAPChunk chunk;
        int chunkLen = reader.read(buffer, 0, DEFAULT_CHUNK_SIZE);
        if (chunkLen == -1) {
            eof = true;
            return new ICAPChunk(new byte[0], true);
        }
        chunk = new ICAPChunk(buffer, false);
        return chunk;
    }
}
