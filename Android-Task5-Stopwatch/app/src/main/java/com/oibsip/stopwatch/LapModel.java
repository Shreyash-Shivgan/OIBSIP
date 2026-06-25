package com.oibsip.stopwatch;

import java.io.Serializable;

/**
 * Model representing a single stopwatch lap item.
 */
public class LapModel implements Serializable {
    private final int lapNumber;
    private final String lapDuration;
    private final String cumulativeTime;

    public LapModel(int lapNumber, String lapDuration, String cumulativeTime) {
        this.lapNumber = lapNumber;
        this.lapDuration = lapDuration;
        this.cumulativeTime = cumulativeTime;
    }

    public int getLapNumber() {
        return lapNumber;
    }

    public String getLapDuration() {
        return lapDuration;
    }

    public String getCumulativeTime() {
        return cumulativeTime;
    }
}
