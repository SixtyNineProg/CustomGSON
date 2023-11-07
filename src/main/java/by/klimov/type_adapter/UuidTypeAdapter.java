package by.klimov.type_adapter;

import java.util.UUID;

public class UuidTypeAdapter implements BaseTypeAdapter {
  @Override
  public <T> boolean isAssignable(T object) {
    return object instanceof UUID;
  }

  @Override
  public <T> T mapStringJsonToObject(String json, Class<T> tClass) {
    return null;
  }

  @Override
  public <T> StringBuilder mapObjectToStringJson(T object) {
    return new StringBuilder("\"" + object.toString() + "\"");
  }
}
