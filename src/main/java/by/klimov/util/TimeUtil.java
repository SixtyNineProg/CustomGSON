package by.klimov.util;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeUtil {

  public static boolean isLocalDate(String string) {
    try {
      LocalDate.parse(string, Constant.LOCAL_DATE_FORMATTER);
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  public static boolean isZonedDateTime(String string) {
    try {
      ZonedDateTime.parse(string, Constant.ZONED_DATE_TIME_FORMATTER);
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }
}
