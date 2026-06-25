package com.oibsip.stopwatch;

import java.util.Locale;

/**
 * Utility for formatting milliseconds to standard stopwatch formats.
 */
public class TimeUtils {

    /**
     * Formats milliseconds to HH:MM:SS format.
     */
    public static String formatTime(long totalMillis) {
        long hours = totalMillis / 3600000;
        long minutes = (totalMillis % 3600000) / 60000;
        long seconds = (totalMillis % 60000) / 1000;

        return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Formats the millisecond fraction (.MS).
     */
    public static String formatMillisFraction(long totalMillis) {
        long ms = (totalMillis % 1000) / 10; // Get hundredths of a second
        return String.format(Locale.US, ".%02d", ms);
    }
}
