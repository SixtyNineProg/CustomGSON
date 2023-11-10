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

  @SuppressWarnings("unchecked")
  @Override
  public Double mapStringJsonToObject(String value) {
    return Double.parseDouble(value);
  }

  @Override
  public <T> StringBuilder mapObjectToStringJson(T object) {
    return new StringBuilder(object.toString());
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
