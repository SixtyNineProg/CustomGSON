package by.klimov.json_type_adapter;

import static by.klimov.util.Constant.LOCAL_DATE_FORMAT;
import static by.klimov.util.StringLiteral.DOUBLE_QUOTE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateTypeAdapter implements BaseTypeAdapter {

  @Override
  public <T> boolean isAssignable(T object) {
    return object instanceof LocalDate;
  }

  @Override
  public <T> T mapStringJsonToObject(String json, Class<T> tClass) {
    return null;
  }

  @Override
  public <T> StringBuilder mapObjectToStringJson(T object) {
    DateTimeFormatter formatters = DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT);
    LocalDate localDate = (LocalDate) object;
    return new StringBuilder(DOUBLE_QUOTE + localDate.format(formatters) + DOUBLE_QUOTE);
  }
}
