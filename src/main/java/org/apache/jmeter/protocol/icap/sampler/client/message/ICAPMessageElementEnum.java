package org.apache.jmeter.protocol.icap.sampler.client.message;

import org.apache.jmeter.protocol.icap.sampler.client.encoder.ICAPCodecUtil;

public enum ICAPMessageElementEnum {
    REQHDR(ICAPCodecUtil.ENCAPSULATION_ELEMENT_REQHDR),
    RESHDR(ICAPCodecUtil.ENCAPSULATION_ELEMENT_RESHDR),
    REQBODY(ICAPCodecUtil.ENCAPSULATION_ELEMENT_REQBODY),
    RESBODY(ICAPCodecUtil.ENCAPSULATION_ELEMENT_RESBODY),
    OPTBODY(ICAPCodecUtil.ENCAPSULATION_ELEMENT_OPTBODY),
    NULLBODY(ICAPCodecUtil.ENCAPSULATION_ELEMENT_NULLBODY);

    private String value;

    ICAPMessageElementEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ICAPMessageElementEnum fromString(String value) {
        if(value != null) {
            for(ICAPMessageElementEnum entryName: ICAPMessageElementEnum.values()) {
                if(value.equalsIgnoreCase(entryName.getValue())) {
                    return entryName;
                }
            }
        }
        return null;
    }
}
