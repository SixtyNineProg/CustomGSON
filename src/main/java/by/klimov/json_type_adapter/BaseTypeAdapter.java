package by.klimov.json_type_adapter;

public interface BaseTypeAdapter {

  <T> boolean isAssignable(T object);

  <T> T mapStringJsonToObject(String json, Class<T> tClass);

  <T> StringBuilder mapObjectToStringJson(T object);
}
