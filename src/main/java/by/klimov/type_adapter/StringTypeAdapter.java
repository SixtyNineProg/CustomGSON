package by.klimov.type_adapter;

import org.apache.commons.text.StringEscapeUtils;

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
  public <T> String mapObjectToStringJson(T object) {
    return "\"" + object + "\"";
  }
}
