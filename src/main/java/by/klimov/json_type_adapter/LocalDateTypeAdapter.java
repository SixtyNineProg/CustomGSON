package by.klimov.json_type_adapter;

import static by.klimov.util.Constant.LOCAL_DATE_FORMAT;
import static by.klimov.util.StringLiteral.DOUBLE_QUOTE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTypeAdapter implements BaseTypeAdapter {

  public static boolean isLocalDate(String string) {
    try {
      LocalDate.parse(string);
      return true;
    } catch (DateTimeParseException e) {
      return false;
    }
  }

  @Override
  public <T> boolean isAssignable(T object) {
    return object instanceof LocalDate;
  }

  @Override
  public boolean isAssignable(String value) {
    return isLocalDate(value);
  }

  @Override
  public <T> T mapStringJsonToObject(String value) {
    return null;
  }

  @Override
  public <T> StringBuilder mapObjectToStringJson(T object) {
    DateTimeFormatter formatters = DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT);
    LocalDate localDate = (LocalDate) object;
    return new StringBuilder(DOUBLE_QUOTE + localDate.format(formatters) + DOUBLE_QUOTE);
  }
}
