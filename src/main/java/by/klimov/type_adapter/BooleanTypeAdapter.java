package by.klimov.type_adapter;

public class BooleanTypeAdapter implements BaseTypeAdapter {

  @Override
  public <T> boolean isAssignable(T object) {
    return object instanceof Boolean;
  }

  @Override
  public <T> T mapStringJsonToObject(String json, Class<T> tClass) {
    return null;
  }

  @Override
  public <T> String mapObjectToStringJson(T object) {
    return object.toString();
  }
}
