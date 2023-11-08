package by.klimov.json_type_adapter;

import static by.klimov.util.StringLiteral.DOUBLE_QUOTE;

public class StringTypeAdapter implements BaseTypeAdapter {

  @Override
  public <T> boolean isAssignable(T object) {
    return object instanceof String;
  }

  @Override
  public <T> T mapStringJsonToObject(String json, Class<T> tClass) {
    return null;
  }

  @Override
  public <T> StringBuilder mapObjectToStringJson(T object) {
    return new StringBuilder(DOUBLE_QUOTE + object + DOUBLE_QUOTE);
  }
}
