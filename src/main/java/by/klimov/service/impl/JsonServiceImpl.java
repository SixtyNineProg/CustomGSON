package by.klimov.service.impl;

import static by.klimov.util.StringLiteral.COLON;
import static by.klimov.util.StringLiteral.COMMA;
import static by.klimov.util.StringLiteral.DOUBLE_QUOTE;
import static by.klimov.util.StringLiteral.LEFT_BRACE;
import static by.klimov.util.StringLiteral.RIGHT_BRACE;

import by.klimov.exception.SerializationException;
import by.klimov.json_type_adapter.BaseTypeAdapter;
import by.klimov.json_type_adapter.factory.TypeAdapterFactory;
import by.klimov.json_type_adapter.factory.TypeAdapterFactoryImpl;
import by.klimov.service.JsonService;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonServiceImpl implements JsonService {

  private final TypeAdapterFactory typeAdapterFactory = new TypeAdapterFactoryImpl();

  @Override
  public <T> T mapJsonToObject(String json, Class<T> tClass) {
    BaseTypeAdapter typeAdapter = typeAdapterFactory.getTypeAdapter(json);
    Map<String, Object> map = typeAdapter.mapStringJsonToObject(json);
    return buildObject(tClass, map);
  }

  @Override
  public <T> String mapObjectToJson(T object) {
    BaseTypeAdapter baseTypeAdapter = typeAdapterFactory.getTypeAdapter(object);
    return baseTypeAdapter.mapObjectToStringJson(object).toString();
  }

  @SuppressWarnings("java:S3011")
  private static <T> void setFiledValue(Field field, T obj, Map<String, Object> map) {
    try {
      field.set(obj, map.get(field.getName()));
    } catch (IllegalAccessException e) {
      throw new SerializationException(e);
    }
  }

  @SuppressWarnings("java:S3011")
  private <T> T buildObject(Class<T> tClass, Map<String, Object> map) {
    T obj;
    obj = getObject(tClass);
    for (Field field : tClass.getDeclaredFields()) {
      field.setAccessible(true);
      if (map.containsKey(field.getName())) {
        setFiledValue(field, obj, map);
      }
    }
    return obj;
  }

  private <T> T getObject(Class<T> tClass) {
    try {
      return tClass.getDeclaredConstructor().newInstance();
    } catch (InstantiationException
        | IllegalAccessException
        | NoSuchMethodException
        | InvocationTargetException e) {
      throw new SerializationException(e);
    }
  }

  private <T> Object getFieldObject(T object, Field field) {
    Object fieldObject;
    try {
      fieldObject = field.get(object);
    } catch (IllegalAccessException e) {
      throw new SerializationException(e);
    }
    return fieldObject;
  }
}
