package com.healthsaas.journaltest2;

import java.io.Serializable;
import java.time.Instant;

public class JournalEntry implements Serializable {
    public enum JournalType {
        SEPSIS
    }

    private JournalType type;
    private String data;
    private Instant timeStamp;

    public JournalType getType() { return type; }

    public void setType(JournalType type) { this.type = type; }

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }

    public Instant getTimeStamp() { return timeStamp; }

    public void setTimeStamp(Instant timeStamp) { this.timeStamp = timeStamp; }
}
