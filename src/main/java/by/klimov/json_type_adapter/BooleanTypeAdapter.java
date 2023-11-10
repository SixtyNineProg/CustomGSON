package by.klimov.json_type_adapter;

public class BooleanTypeAdapter implements BaseTypeAdapter {

  @Override
  public <T> boolean isAssignable(T object) {
    return object instanceof Boolean;
  }

  @Override
  public boolean isAssignable(String value) {
    return value.equals(Boolean.TRUE.toString()) || value.equals(Boolean.FALSE.toString());
  }

  @SuppressWarnings("unchecked")
  @Override
  public Boolean mapStringJsonToObject(String value) {
    return Boolean.valueOf(value);
  }

  @Override
  public <T> StringBuilder mapObjectToStringJson(T object) {
    return new StringBuilder(object.toString());
  }
}
