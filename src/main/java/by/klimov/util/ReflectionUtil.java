package by.klimov.util;

import by.klimov.exception.SerializationException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReflectionUtil {

  public static boolean isExistParameterizedGenericType(Field field) {
    Type fieldType = field.getGenericType();
    return fieldType instanceof ParameterizedType;
  }

  public static Class<?> getClass(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new SerializationException(e);
    }
  }

  public static <T> Object getFieldObject(T object, Field field) {
    Object fieldObject;
    try {
      fieldObject = field.get(object);
    } catch (IllegalAccessException e) {
      throw new SerializationException(e);
    }
    return fieldObject;
  }

  @SuppressWarnings("java:S3011")
  public static <T> T buildObject(Class<T> tClass, Map<String, Object> map) {
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
  public static Field getField(String key, Class<?> tClass) {
    return Arrays.stream(tClass.getDeclaredFields())
        .peek(field -> field.setAccessible(true))
        .filter(field -> field.getName().equals(key))
        .findFirst()
        .orElseThrow();
  }

  public static <T> T getObject(Class<T> tClass) {
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
  public static <T> void setFiledValue(Field field, T obj, Map<String, Object> map) {
    try {
      field.set(obj, map.get(field.getName()));
    } catch (IllegalAccessException e) {
      throw new SerializationException(e);
    }
  }
}
