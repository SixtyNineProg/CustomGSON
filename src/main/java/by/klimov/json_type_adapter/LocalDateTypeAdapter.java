package by.klimov.json_type_adapter;

import static by.klimov.util.Constant.LOCAL_DATE_FORMAT;
import static by.klimov.util.StringLiteral.DOUBLE_QUOTE;

import by.klimov.util.Constant;
import by.klimov.util.StringUtil;
import by.klimov.util.TimeUtil;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class LocalDateTypeAdapter implements BaseTypeAdapter {

  @Override
  public <T> boolean isAssignable(T object) {
    return object instanceof LocalDate;
  }

  @Override
  public boolean isAssignable(String value) {
    String extractedValue = StringUtil.extractString(value, Constant.STRING_REGEX);
    return Objects.isNull(extractedValue)
        ? TimeUtil.isLocalDate(value)
        : TimeUtil.isLocalDate(extractedValue) || TimeUtil.isLocalDate(value);
  }

  @Override
  public LocalDate mapStringJsonToObject(String value) {
    String extractedValue = StringUtil.extractString(value, Constant.STRING_REGEX);
    return Objects.isNull(extractedValue)
        ? LocalDate.parse(value, Constant.LOCAL_DATE_FORMATTER)
        : LocalDate.parse(extractedValue, Constant.LOCAL_DATE_FORMATTER);
  }

  @Override
  public <T> StringBuilder mapObjectToStringJson(T object) {
    DateTimeFormatter formatters = DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT);
    LocalDate localDate = (LocalDate) object;
    return new StringBuilder(DOUBLE_QUOTE + localDate.format(formatters) + DOUBLE_QUOTE);
  }
}
