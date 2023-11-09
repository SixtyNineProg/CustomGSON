package by.klimov.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeUtil {

    public static boolean isLocalDate(String string) {
        try {
            LocalDate.parse(string);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isZonedDateTime(String string) {
        try {
            ZonedDateTime.parse(string);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
