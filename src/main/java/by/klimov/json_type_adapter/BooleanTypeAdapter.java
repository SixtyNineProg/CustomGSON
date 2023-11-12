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

  @Override
  public <T> boolean isAssignable(Class<T> tClass) {
    return tClass.equals(Boolean.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T mapStringJsonToObject(String value, Class<T> tClass) {
    return (T) Boolean.valueOf(value);
  }

  @Override
  public <T> StringBuilder mapObjectToStringJson(T object) {
    return new StringBuilder(object.toString());
  }

  @Override
  public Class<?> getClassType() {
    return Boolean.class;
  }
}
