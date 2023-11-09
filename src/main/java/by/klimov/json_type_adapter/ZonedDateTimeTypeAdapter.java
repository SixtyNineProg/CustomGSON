package by.klimov.json_type_adapter;

import static by.klimov.util.Constant.ZONED_DATE_TIME_FORMAT;
import static by.klimov.util.StringLiteral.DOUBLE_QUOTE;

import by.klimov.util.Constant;
import by.klimov.util.StringUtil;
import by.klimov.util.TimeUtil;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ZonedDateTimeTypeAdapter implements BaseTypeAdapter {

  @Override
  public <T> boolean isAssignable(T object) {
    return object instanceof ZonedDateTime;
  }

  @Override
  public boolean isAssignable(String value) {
    String extractedValue = StringUtil.extractString(value, Constant.STRING_REGEX);
    return Objects.isNull(extractedValue)
        ? TimeUtil.isZonedDateTime(value)
        : TimeUtil.isZonedDateTime(extractedValue) || TimeUtil.isZonedDateTime(value);
  }

  @Override
  public ZonedDateTime mapStringJsonToObject(String value) {
    String extractedValue = StringUtil.extractString(value, Constant.STRING_REGEX);
    return Objects.isNull(extractedValue)
        ? ZonedDateTime.parse(value, Constant.ZONED_DATE_TIME_FORMATTER)
        : ZonedDateTime.parse(extractedValue, Constant.ZONED_DATE_TIME_FORMATTER);
  }

  @Override
  public <T> StringBuilder mapObjectToStringJson(T object) {
    DateTimeFormatter formatters = DateTimeFormatter.ofPattern(ZONED_DATE_TIME_FORMAT);
    ZonedDateTime zonedDateTime = (ZonedDateTime) object;
    return new StringBuilder(DOUBLE_QUOTE + zonedDateTime.format(formatters) + DOUBLE_QUOTE);
  }
}
