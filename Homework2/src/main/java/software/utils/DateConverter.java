package software.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class DateConverter {
    private static final ZoneId current = ZoneId.of("Europe/Moscow");

    public static List<Long> convertTime(Integer hours) {
        ZonedDateTime now = ZonedDateTime.now(current);
        List<Long> timestamps = new ArrayList<>();
        for (int i = hours; i >= 0; i--) {
            timestamps.add(now.minusHours(i).toEpochSecond());
        }
        return timestamps;
    }
}
