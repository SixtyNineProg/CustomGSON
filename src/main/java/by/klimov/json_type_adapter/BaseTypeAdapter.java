package by.klimov.json_type_adapter;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

public interface BaseTypeAdapter {

  <T> boolean isAssignable(T object);

  boolean isAssignable(String value);

  <T> boolean isAssignable(Class<T> tClass);

  <T> T mapStringJsonToObject(String value, Class<T> tClass);

  <T> T mapStringJsonToObject(String value, Field field);

  <T> StringBuilder mapObjectToStringJson(T object);
}
