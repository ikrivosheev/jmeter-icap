package org.apache.jmeter.protocol.icap.sampler.client.codecs;

import org.apache.jmeter.protocol.icap.sampler.client.http.HTTPRequest;
import org.apache.jmeter.protocol.icap.sampler.client.http.HTTPResponse;
import org.apache.jmeter.protocol.icap.sampler.client.message.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;


public class ICAPMessageEncoder {

    private static Logger logger = LogManager.getLogger(ICAPMessageEncoder.class);

    public void encode(OutputStream outputStream, ICAPRequest message) throws Exception {
        logger.debug("Encoding [" + message.getClass().getName() + "]");
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        encodeInitialLine(buffer, message);
        encodeHeaders(buffer, message);
        ByteArrayOutputStream httpRequestBuffer = encodeHttpRequestHeader(message.getHttpRequest());
        ByteArrayOutputStream httpResponseBuffer = encodeHttpResponseHeader(message.getHttpResponse());
        int index = 0;
        Encapsulated encapsulated = new Encapsulated();
        if(httpRequestBuffer.size() > 0) {
            encapsulated.addEntry(ICAPMessageElementEnum.REQHDR, index);
            httpRequestBuffer.write(ICAPCodecUtil.CRLF);
            index += httpRequestBuffer.size();
        }
        if(httpResponseBuffer.size() > 0) {
            encapsulated.addEntry(ICAPMessageElementEnum.RESHDR, index);
            httpResponseBuffer.write(ICAPCodecUtil.CRLF);
            index += httpResponseBuffer.size();
        }

        if(message.getBodyType() != null) {
            encapsulated.addEntry(message.getBodyType(), index);
        } else {
            encapsulated.addEntry(ICAPMessageElementEnum.NULLBODY, index);
        }

        encodeEncapsulated(buffer, encapsulated);
        buffer.write(httpRequestBuffer.toByteArray());
        buffer.write(httpResponseBuffer.toByteArray());
        buffer.writeTo(outputStream);
        encodeBody(outputStream, message);
    }

    private int encodeInitialLine(ByteArrayOutputStream buffer, ICAPRequest request) throws Exception {
        int index = buffer.size();
        buffer.write(request.getMethod().toString().getBytes(ICAPCodecUtil.ASCII_CHARSET));
        buffer.write(ICAPCodecUtil.SPACE);
        buffer.write(request.getUri().toString().getBytes(ICAPCodecUtil.ASCII_CHARSET));
        buffer.write(ICAPCodecUtil.SPACE);
        buffer.write(request.getVersion().toString().getBytes(ICAPCodecUtil.ASCII_CHARSET));
        buffer.write(ICAPCodecUtil.CRLF);
        return buffer.size() - index;
    }

    private void encodeBody(OutputStream outputStream, ICAPRequest request) throws Exception {
        ICAPRequestBody body = request.getBody();
        while (true) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ICAPChunk chunk = body.readChunk();

            if (chunk.isLast()) {
                buffer.write((byte) '0');
                buffer.write(ICAPCodecUtil.CRLF);
                buffer.write(ICAPCodecUtil.CRLF);
                buffer.writeTo(outputStream);
                body.close();
                return;
            }
            else {
                buffer.write(Integer.toHexString(chunk.size()).getBytes(ICAPCodecUtil.ASCII_CHARSET));
                buffer.write(ICAPCodecUtil.CRLF);
                chunk.getContent().writeTo(buffer);
                buffer.write(ICAPCodecUtil.CRLF);
                buffer.writeTo(outputStream);
            }
        }
    }

    private ByteArrayOutputStream encodeHttpRequestHeader(HTTPRequest httpRequest) throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        if(httpRequest != null) {
            buffer.write(httpRequest.getMethod().toString().getBytes(ICAPCodecUtil.ASCII_CHARSET));
            buffer.write(ICAPCodecUtil.SPACE);
            buffer.write(httpRequest.getUri().toString().getBytes(ICAPCodecUtil.ASCII_CHARSET));
            buffer.write(ICAPCodecUtil.SPACE);
            buffer.write(httpRequest.getVersion().toString().getBytes(ICAPCodecUtil.ASCII_CHARSET));
            buffer.write(ICAPCodecUtil.CRLF);
            for (Map.Entry<String, String> h: httpRequest.getHeaders()) {
                encodeHeader(buffer, h.getKey(), h.getValue());
            }
        }
        return buffer;
    }

    private ByteArrayOutputStream encodeHttpResponseHeader(HTTPResponse httpResponse) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        if(httpResponse != null) {
            buffer.write(httpResponse.getVersion().toString().getBytes(ICAPCodecUtil.ASCII_CHARSET));
            buffer.write(ICAPCodecUtil.SPACE);
            buffer.write(httpResponse.getStatus().toString().getBytes(ICAPCodecUtil.ASCII_CHARSET));
            buffer.write(ICAPCodecUtil.CRLF);
            for (Map.Entry<String, String> h: httpResponse.getHeaders()) {
                encodeHeader(buffer, h.getKey(), h.getValue());
            }
        }
        return buffer;
    }

    private int encodeHeaders(ByteArrayOutputStream buffer, AbstractICAPMessage message) throws IOException {
        int size = buffer.size();
        for (Map.Entry<String, String> h: message.getHeaders()) {
            encodeHeader(buffer, h.getKey(), h.getValue());
        }
        return buffer.size() - size;
    }

    private int encodeHeader(ByteArrayOutputStream buffer, String header, String value) throws IOException {
        int size = buffer.size();
        buffer.write(header.getBytes(ICAPCodecUtil.ASCII_CHARSET));
        buffer.write(ICAPCodecUtil.COLON);
        buffer.write(ICAPCodecUtil.SPACE);
        buffer.write(value.getBytes(ICAPCodecUtil.ASCII_CHARSET));
        buffer.write(ICAPCodecUtil.CRLF);
        return buffer.size() - size;
    }

    private int encodeEncapsulated(ByteArrayOutputStream buffer, Encapsulated encapsulated) throws IOException {
        int size = buffer.size();
        buffer.write("Encapsulated: ".getBytes(ICAPCodecUtil.ASCII_CHARSET));
        Iterator<Encapsulated.Entry> entryIterator = encapsulated.iterator();
        while(entryIterator.hasNext()) {
            Encapsulated.Entry entry = entryIterator.next();
            buffer.write(entry.getName().getValue().getBytes(ICAPCodecUtil.ASCII_CHARSET));
            buffer.write("=".getBytes(ICAPCodecUtil.ASCII_CHARSET));
            buffer.write(Integer.toString(entry.getPosition()).getBytes(ICAPCodecUtil.ASCII_CHARSET));
            if(entryIterator.hasNext()) {
                buffer.write(',');
                buffer.write(ICAPCodecUtil.SPACE);
            }
        }
        buffer.write(ICAPCodecUtil.CRLF);
        buffer.write(ICAPCodecUtil.CRLF);
        return buffer.size() - size;
    }
}
