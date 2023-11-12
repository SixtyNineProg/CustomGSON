package by.klimov.util;

import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constant {

  public static final String LOCAL_DATE_FORMAT = "yyyy-MM-dd";
  public static final String ZONED_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss a z";

  public static final DateTimeFormatter ZONED_DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern(Constant.ZONED_DATE_TIME_FORMAT);

  public static final DateTimeFormatter LOCAL_DATE_FORMATTER =
      DateTimeFormatter.ofPattern(Constant.LOCAL_DATE_FORMAT);

  public static final String STRING_REGEX = "\"(.*?)\"";
}
