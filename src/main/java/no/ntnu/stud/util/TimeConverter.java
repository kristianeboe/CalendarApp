package no.ntnu.stud.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by adrianh on 23.02.15.
 */
public class TimeConverter {

    /**
     * @param time
     * @return
     */
    public static Timestamp localDateTimeToTimestamp(LocalDateTime time) {
        return Timestamp.valueOf(time);
    }

    public static LocalDateTime timestampToLocalDateTime(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }
}
