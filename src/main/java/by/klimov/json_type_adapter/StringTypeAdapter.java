package by.klimov.json_type_adapter;

import static by.klimov.util.StringLiteral.DOUBLE_QUOTE;

public class StringTypeAdapter implements BaseTypeAdapter {

  @Override
  public <T> boolean isAssignable(T object) {
    return object instanceof String;
  }

  @Override
  public boolean isAssignable(String value) {
    return value.startsWith("\"");
  }

  @Override
  public String mapStringJsonToObject(String value) {
    return value.substring(1, value.length() - 1);
  }

  @Override
  public <T> StringBuilder mapObjectToStringJson(T object) {
    return new StringBuilder(DOUBLE_QUOTE + object + DOUBLE_QUOTE);
  }
}
