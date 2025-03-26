package com.andreidodu.sbpv.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeUtil {

    public static final String TIME_HUMANABLE_PATTERN = "%d:%02d:%02d";

    public static String secondsToHumanableString(long seconds) {
        return String.format(TIME_HUMANABLE_PATTERN, seconds / 3600, (seconds % 3600) / 60, (seconds % 60));
    }

    public static long calculateTimeElapsedInSeconds(LocalDateTime dateTime) {
        Duration duration = Duration.between(dateTime, LocalDateTime.now());
        return duration.getSeconds();
    }
}
