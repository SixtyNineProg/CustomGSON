package by.klimov.json_type_adapter;

public class DoubleTypeAdapter implements BaseTypeAdapter {

  @Override
  public <T> boolean isAssignable(T object) {
    return object instanceof Double;
  }

  @Override
  public <T> T mapStringJsonToObject(String json, Class<T> tClass) {
    return null;
  }

  @Override
  public <T> StringBuilder mapObjectToStringJson(T object) {
    return new StringBuilder(object.toString());
  }
}
