package org.apache.jmeter.protocol.icap.sampler.client.message;

import com.sun.istack.NotNull;
import org.apache.jmeter.protocol.icap.sampler.client.codecs.ICAPDecodingError;

import java.util.*;


public final class Encapsulated implements Iterable<Encapsulated.Entry> {

    private List<Entry> entries;

    public Encapsulated() {
        entries = new ArrayList<>();
    }

    public Encapsulated(String headerValue) throws ICAPDecodingError {
        this();
        parseHeaderValue(headerValue);
    }

    public boolean containsEntry(ICAPMessageElementEnum entity) {
        for(Entry entry : entries) {
            if(entry.getName().equals(entity)) {
                return true;
            }
        }
        return false;
    }

    public ICAPMessageElementEnum containsBodyEntry() {
        ICAPMessageElementEnum body = null;
        for(Entry entry : entries) {
            if(entry.getName().equals(ICAPMessageElementEnum.OPTBODY)) {
                body = entry.getName();
                break;
            } else if(entry.getName().equals(ICAPMessageElementEnum.REQBODY)) {
                body = entry.getName();
                break;
            } else if(entry.getName().equals(ICAPMessageElementEnum.RESBODY)) {
                body = entry.getName();
                break;
            } else if(entry.getName().equals(ICAPMessageElementEnum.NULLBODY)) {
                body = entry.getName();
                break;
            }
        }
        return body;
    }

    public void addEntry(ICAPMessageElementEnum name, int position) {
        Entry entry = new Entry(name,position);
        entries.add(entry);
        Collections.sort(entries);
    }

    @Override
    public Iterator<Entry> iterator() {
        return entries.iterator();
    }

    /*
        REQMOD request: 	 [req-hdr] req-body
        REQMOD response: 	{[req-hdr] req-body} | {[res-hdr] res-body}
        RESPMOD request:	 [req-hdr] [res-hdr] res-body
        RESPMOD response:	 [res-hdr] res-body
        OPTIONS response:	 opt-body
    */
    private void parseHeaderValue(String headerValue) throws ICAPDecodingError {
        if(headerValue == null) {
            throw new ICAPDecodingError("No value associated with Encapsualted header");
        }
        StringTokenizer tokenizer = new StringTokenizer(headerValue,",");
        while(tokenizer.hasMoreTokens()) {
            String parameterString = tokenizer.nextToken();
            if(parameterString != null) {
                String[] parameter = splitParameter(parameterString.trim());
                try {
                    int value = Integer.parseInt(parameter[1]);
                    Entry entry = new Entry(ICAPMessageElementEnum.fromString(parameter[0]),value);
                    entries.add(entry);
                } catch(NumberFormatException nfe) {
                    throw new ICAPDecodingError("the Encapsulated header value [" + parameter[1] + "] for the key [" + parameter[0] + "] is not a number");
                }
            }
        }
        Collections.sort(entries);
    }

    private String[] splitParameter(String parameter) throws ICAPDecodingError {
        int offset = parameter.indexOf('=');
        if(offset <= 0) {
            throw new ICAPDecodingError("Encapsulated header value was not understood [" + parameter + "]");
        }
        String key = parameter.substring(0,offset);
        String value = parameter.substring(offset + 1,parameter.length());
        if(value.contains(",")) {
            value = value.substring(0,value.indexOf(','));
        }
        return new String[]{key.trim(),value};
    }

    private Entry getEntryByName(ICAPMessageElementEnum entryName) {
        Entry returnValue = null;
        for(Entry entry : entries) {
            if(entry.getName().equals(entryName)) {
                returnValue = entry;
                break;
            }
        }
        return returnValue;
    }

    public final static class Entry implements Comparable<Entry> {

        private final ICAPMessageElementEnum name;
        private final Integer position;

        public Entry(ICAPMessageElementEnum name, Integer position) {
            this.name = name;
            this.position = position;
        }

        public ICAPMessageElementEnum getName() {
            return name;
        }

        public int getPosition() {
            return position;
        }

        @Override
        @NotNull
        public int compareTo(Entry entry) {
            if(this.name.equals(ICAPMessageElementEnum.NULLBODY)) {
                return 1;
            }
            return this.position.compareTo(entry.position);
        }

        @Override
        public String toString() {
            return name + "=" + position;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Encapsulated: ");
        for(Entry entry : entries) {
            if(entry != null) {
                builder.append(" [").append(entry.toString()).append("] ");
            }
        }
        return builder.toString();
    }
}
