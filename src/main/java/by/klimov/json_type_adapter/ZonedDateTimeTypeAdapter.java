package by.klimov.json_type_adapter;

import static by.klimov.util.Constant.ZONED_DATE_TIME_FORMAT;
import static by.klimov.util.StringLiteral.DOUBLE_QUOTE;

import by.klimov.util.TimeUtil;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeTypeAdapter implements BaseTypeAdapter {

  @Override
  public <T> boolean isAssignable(T object) {
    return object instanceof ZonedDateTime;
  }

  @Override
  public boolean isAssignable(String value) {
    return TimeUtil.isZonedDateTime(value);
  }

  @Override
  public <T> T mapStringJsonToObject(String value) {
    return null;
  }

  @Override
  public <T> StringBuilder mapObjectToStringJson(T object) {
    DateTimeFormatter formatters = DateTimeFormatter.ofPattern(ZONED_DATE_TIME_FORMAT);
    ZonedDateTime zonedDateTime = (ZonedDateTime) object;
    return new StringBuilder(DOUBLE_QUOTE + zonedDateTime.format(formatters) + DOUBLE_QUOTE);
  }
}
