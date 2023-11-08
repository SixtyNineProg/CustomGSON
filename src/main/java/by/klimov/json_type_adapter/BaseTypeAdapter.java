package by.klimov.json_type_adapter;

public interface BaseTypeAdapter {

  <T> boolean isAssignable(T object);

  boolean isAssignable(String value);

  <T> T mapStringJsonToObject(String value);

  <T> StringBuilder mapObjectToStringJson(T object);
}
