package by.klimov.json_type_adapter;

public class DoubleTypeAdapter implements BaseTypeAdapter {

  @Override
  public <T> boolean isAssignable(T object) {
    return object instanceof Double;
  }

  @Override
  public boolean isAssignable(String value) {
    return isDouble(value);
  }

  @Override
  public <T> boolean isAssignable(Class<T> tClass) {
    return tClass.equals(Double.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T mapStringJsonToObject(String value, Class<T> tClass) {
    return (T) Double.valueOf(value);
  }

  @Override
  public <T> StringBuilder mapObjectToStringJson(T object) {
    return new StringBuilder(object.toString());
  }

  @Override
  public Class<?> getClassType() {
    return Double.class;
  }

  private boolean isDouble(String s) {
    try {
      Double.parseDouble(s);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
