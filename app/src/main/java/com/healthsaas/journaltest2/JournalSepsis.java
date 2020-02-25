package com.healthsaas.journaltest2;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class JournalSepsis {
    private JournalEntry entry;
    private static final String SEPSIS_DATA = "";

    public JournalSepsis() {
        entry = new JournalEntry();
        entry.setType(JournalEntry.JournalType.SEPSIS);
        entry.setTimeStamp(Instant.now());
        entry.setData(SEPSIS_DATA);
    }

    public JournalEntry getSepsisEntry() {
        return entry;
    }

    private enum SepsisResult {
        NOT_ANXIOUS(5820),
        ANXIOUS(3475),
        HYPERAWARE(3478),
        SOMETHING_WRONG(5821),
        SENSE_OF_DOOM(3476),
        INVALID(-1);

        private int value;
        private static Map map = new HashMap<>();

        private SepsisResult(int value) {
            this.value = value;
        }

        static {
            for (SepsisResult pageType : SepsisResult.values()) {
                map.put(pageType.value, pageType);
            }
        }

        public static SepsisResult valueOf(int pageType) {
            return (SepsisResult) map.get(pageType);
        }

        public int getValue() {
            return value;
        }
    }

    public static String getSepsisResponse(int radioButtonId) {
        int resultCode;

        switch (radioButtonId) {
            case R.id.radio_not_anxious:
                resultCode = SepsisResult.NOT_ANXIOUS.value;
                break;
            case R.id.radio_anxious:
                resultCode = SepsisResult.ANXIOUS.value;
                break;
            case R.id.radio_hyperaware:
                resultCode = SepsisResult.HYPERAWARE.value;
                break;
            case R.id.radio_something_wrong:
                resultCode = SepsisResult.SOMETHING_WRONG.value;
                break;
            case R.id.radio_doom:
                resultCode = SepsisResult.SENSE_OF_DOOM.value;
                break;
            default:
                resultCode = SepsisResult.INVALID.value;
        }

        // Build the response here
        return "284,687," + resultCode;
    }
}
