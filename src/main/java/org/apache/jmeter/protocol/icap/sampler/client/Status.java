package org.apache.jmeter.protocol.icap.sampler.client;

public class Status {
    private String status;
    private String reason;

    public Status(String status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return status + " " + reason;
    }
}
