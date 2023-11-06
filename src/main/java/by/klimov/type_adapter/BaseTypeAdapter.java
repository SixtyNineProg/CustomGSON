package by.klimov.type_adapter;

import java.lang.reflect.Type;

public interface BaseTypeAdapter {

  <T> boolean isAssignable(T object);

  <T> T mapStringJsonToObject(String json, Class<T> tClass);

  <T> String mapObjectToStringJson(T object);
}
