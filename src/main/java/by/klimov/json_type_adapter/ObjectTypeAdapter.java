package by.klimov.json_type_adapter;

import static by.klimov.util.StringLiteral.COLON;
import static by.klimov.util.StringLiteral.COMMA;
import static by.klimov.util.StringLiteral.DOUBLE_QUOTE;
import static by.klimov.util.StringLiteral.LEFT_BRACE;
import static by.klimov.util.StringLiteral.RIGHT_BRACE;

import by.klimov.exception.SerializationException;
import by.klimov.json_type_adapter.factory.TypeAdapterFactory;
import by.klimov.json_type_adapter.factory.impl.TypeAdapterFactoryImpl;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjectTypeAdapter implements BaseTypeAdapter {

  private final TypeAdapterFactory typeAdapterFactory = new TypeAdapterFactoryImpl();

  @Override
  public <T> boolean isAssignable(T object) {
    return true;
  }

  @Override
  public boolean isAssignable(String value) {
    return false;
  }

  @Override
  public <T> boolean isAssignable(Class<T> tClass) {
    return tClass.equals(Object.class);
  }

  @Override
  public <T> T mapStringJsonToObject(String json, Class<T> tClass) {
    try {
      BaseTypeAdapter baseTypeAdapter = typeAdapterFactory.getTypeAdapter(tClass);
      return baseTypeAdapter.mapStringJsonToObject(json, tClass);
    } catch (NoSuchElementException e) {
      Map<String, Object> map = new HashMap<>();
      Pattern pattern =
              Pattern.compile("\"(.*?)\":\\s*(\".*?\"|\\d*[.]?\\d+|true|false|\\{.*?\\}|\\[.*?\\])");
      Matcher matcher = pattern.matcher(json);
      while (matcher.find()) {
        String key = matcher.group(1);
        String value = matcher.group(2);
        Class<?> fieldClass = getFieldClassByName(key, tClass);
        BaseTypeAdapter typeAdapter = typeAdapterFactory.getTypeAdapter(value, fieldClass);
        map.put(key, typeAdapter.mapStringJsonToObject(value, fieldClass));
      }
      return buildObject(tClass, map);
    }
  }

  @SuppressWarnings("java:S3011")
  @Override
  public <T> StringBuilder mapObjectToStringJson(T object) {
    StringBuilder sb = new StringBuilder(LEFT_BRACE);
    List<Field> fields = Arrays.stream(object.getClass().getDeclaredFields()).toList();
    for (Iterator<Field> iterator = fields.iterator(); iterator.hasNext(); ) {
      Field field = iterator.next();
      field.setAccessible(true);
      Object fieldObject = getFieldObject(object, field);
      BaseTypeAdapter baseTypeAdapter = typeAdapterFactory.getTypeAdapter(fieldObject);
      sb.append(DOUBLE_QUOTE)
          .append(field.getName())
          .append(DOUBLE_QUOTE)
          .append(COLON)
          .append(baseTypeAdapter.mapObjectToStringJson(fieldObject));
      if (iterator.hasNext()) {
        sb.append(COMMA);
      }
    }
    sb.append(RIGHT_BRACE);
    return sb;
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

  @SuppressWarnings("java:S3011")
  private <T> T buildObject(Class<T> tClass, Map<String, Object> map) {
    T obj = getObject(tClass);
    for (Field field : tClass.getDeclaredFields()) {
      field.setAccessible(true);
      if (map.containsKey(field.getName())) {
        setFiledValue(field, obj, map);
      }
    }
    return obj;
  }

  @SuppressWarnings({"java:S3864", "java:S3011"})
  private Class<?> getFieldClassByName(String key, Class<?> tClass) {
    return Arrays.stream(tClass.getDeclaredFields())
            .peek(field -> field.setAccessible(true))
            .filter(field -> field.getName().equals(key))
            .findFirst()
            .orElseThrow()
            .getType();
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

  @SuppressWarnings("java:S3011")
  private <T> void setFiledValue(Field field, T obj, Map<String, Object> map) {
    try {
      field.set(obj, map.get(field.getName()));
    } catch (IllegalAccessException e) {
      throw new SerializationException(e);
    }
  }
}
