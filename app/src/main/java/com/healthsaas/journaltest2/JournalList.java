package com.healthsaas.journaltest2;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;

public class JournalList implements Serializable {
    private ArrayList<JournalEntry> pendingJournals;

    public JournalList() {
        pendingJournals = new ArrayList<JournalEntry>();
    }

    public ArrayList<JournalEntry> getPendingJournals() {
        return pendingJournals;
    }

    public void setPendingJournals(ArrayList<JournalEntry> pendingJournals) {
        this.pendingJournals = pendingJournals;
    }

    /**
     * Used to add a new Journal Entry
     * @param entry - The JournalEntry to add
     */
    public void addJournalEntry(JournalEntry entry) {
        if (entry != null) {
            if (entry.getTimeStamp() == null) {
                // Add a timestamp
                entry.setTimeStamp(Instant.now());
            }
            pendingJournals.add(entry);
        }
    }

    /**
     * Check how many journal entries are in the list
     * @return
     */
    public int journalsPending() {
        return pendingJournals.size();
    }
}
