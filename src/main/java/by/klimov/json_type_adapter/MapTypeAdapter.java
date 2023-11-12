package by.klimov.json_type_adapter;

import static by.klimov.util.StringLiteral.COLON;
import static by.klimov.util.StringLiteral.COMMA;
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
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapTypeAdapter implements BaseTypeAdapter {

  @Override
  public <T> boolean isAssignable(T object) {
    return object instanceof Map<?, ?>;
  }

  @Override
  public boolean isAssignable(String value) {
    return value.startsWith(LEFT_BRACE);
  }

  @Override
  public <T> boolean isAssignable(Class<T> tClass) {
    return tClass.equals(Map.class);
  }

  @Override
  public <T> T mapStringJsonToObject(String json, Class<T> tClass) {
    TypeAdapterFactory typeAdapterFactory = new TypeAdapterFactoryImpl();
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

  @Override
  public <T> T mapStringJsonToObject(String value, Field field) {
    return null;
  }

  @Override
  public <T> StringBuilder mapObjectToStringJson(T object) {
    Map<?, ?> map = (Map<?, ?>) object;
    StringBuilder stringBuilder = new StringBuilder(LEFT_BRACE);
    Set<? extends Map.Entry<?, ?>> entrySet = map.entrySet();
    for (Iterator<? extends Map.Entry<?, ?>> iterator = entrySet.iterator(); iterator.hasNext(); ) {
      TypeAdapterFactory typeAdapterFactory = new TypeAdapterFactoryImpl();
      Map.Entry<?, ?> iteratorObject = iterator.next();

      Object key = iteratorObject.getKey();
      BaseTypeAdapter baseTypeAdapter = typeAdapterFactory.getTypeAdapter(key);
      stringBuilder.append(baseTypeAdapter.mapObjectToStringJson(key));

      stringBuilder.append(COLON);

      Object value = iteratorObject.getValue();
      baseTypeAdapter = typeAdapterFactory.getTypeAdapter(value);
      stringBuilder.append(baseTypeAdapter.mapObjectToStringJson(value));

      if (iterator.hasNext()) {
        stringBuilder.append(COMMA);
      }
    }
    stringBuilder.append(RIGHT_BRACE);
    return stringBuilder;
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

  private boolean isValidJson(String json) {
    String regex = "\\A\\s*?(\\{.*\\}|\\[.*\\])\\s*?\\Z";
    Pattern pattern = Pattern.compile(regex);
    return pattern.matcher(json).matches();
  }
}
