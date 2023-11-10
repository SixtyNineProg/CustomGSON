package by.klimov.json_type_adapter;

import java.util.Objects;

public class NullTypeAdapter implements BaseTypeAdapter {

  public static final String STRING_NULL = "null";

  @Override
  public <T> boolean isAssignable(T object) {
    return Objects.isNull(object);
  }

  @Override
  public boolean isAssignable(String value) {
    return value.isEmpty() || value.isBlank();
  }

  @Override
  public <T> boolean isAssignable(Class<T> tClass) {
    return false;
  }

  @Override
  public <T> T mapStringJsonToObject(String value, Class<T> tClass) {
    return null;
  }

  @Override
  public <T> StringBuilder mapObjectToStringJson(T object) {
    return new StringBuilder(STRING_NULL);
  }
}
