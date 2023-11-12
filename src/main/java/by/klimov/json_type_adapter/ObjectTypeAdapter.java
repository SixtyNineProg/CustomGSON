package by.klimov.json_type_adapter;

import static by.klimov.util.StringLiteral.COLON;
import static by.klimov.util.StringLiteral.COMMA;
import static by.klimov.util.StringLiteral.DOUBLE_QUOTE;
import static by.klimov.util.StringLiteral.LEFT_BRACE;
import static by.klimov.util.StringLiteral.LEFT_BRACKET;
import static by.klimov.util.StringLiteral.RIGHT_BRACE;
import static by.klimov.util.StringLiteral.RIGHT_BRACKET;

import by.klimov.exception.SerializationException;
import by.klimov.json_type_adapter.factory.TypeAdapterFactory;
import by.klimov.json_type_adapter.factory.impl.TypeAdapterFactoryImpl;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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
      Map<String, String> jsonData = parseJson(json);
      for (Map.Entry<String, String> jsonObject : jsonData.entrySet()) {
        String key = jsonObject.getKey();
        String value = jsonObject.getValue();
        Field field = getField(key, tClass);
        if (isExistParameterizedGenericType(field)) {
          List<Type> types = getParameterizedTypes(field);
          ParameterizedType type = (ParameterizedType) types.get(0);
          List<Type> actualTypeArguments = List.of(type.getActualTypeArguments());
          Class<?> rawTypeClass = getClass(type.getRawType().getTypeName());
          CollectionBaseTypeAdapter typeAdapter =
              typeAdapterFactory.getCollectionTypeAdapter(rawTypeClass);
          map.put(key, typeAdapter.mapStringJsonToObject(value, actualTypeArguments));
        } else {
          Class<?> fieldClass = field.getType();
          BaseTypeAdapter typeAdapter = typeAdapterFactory.getTypeAdapter(value, fieldClass);
          map.put(key, typeAdapter.mapStringJsonToObject(value, fieldClass));
        }
      }
      return buildObject(tClass, map);
    }
  }

  private Map<String, String> parseJson(String json) {
    json = json.trim();
    if (json.startsWith(LEFT_BRACKET) && json.endsWith(RIGHT_BRACKET)) {
      return Map.of("", json);
    }
    json = removeStarAndEndBraces(json);
    Map<String, String> map = new HashMap<>();
    String key = "";
    String value;
    int bracketCounter = 0;
    int braceCounter = 0;
    boolean isKey = true;
    int keyStart = json.indexOf("\"") + 1;
    for (int i = keyStart; i < json.length(); i++) {
      char c = json.charAt(i);
      if (isKey) {
        if (isEndKey(json, c, i)) {
          isKey = false;
          key = json.substring(keyStart, i).trim();
          keyStart = i + 2;
        }
      } else {
        braceCounter = c == '{' ? braceCounter + 1: braceCounter;
        braceCounter = c == '}' ? braceCounter - 1: braceCounter;
        bracketCounter = c == '[' ? bracketCounter + 1: bracketCounter;
        bracketCounter = c == ']' ? bracketCounter + 1: bracketCounter;
        if (bracketCounter == 0 && braceCounter == 0) {
          if (isEndValue(json, c, i)) {
            isKey = true;
            value = json.substring(keyStart, i).replace("\"", "").trim();
            keyStart = i + 2;
            map.put(key, value);
          }
          if (isEndJson(json, i)) {
            value = json.substring(keyStart).replace("\"", "").trim();
            map.put(key, value);
          }
        }
      }
    }
    return map;
  }

  private boolean isEndJson(String json, int i) {
    return i + 1 == json.length();
  }

  private String removeStarAndEndBraces(String json) {
    return json.substring(1, json.length() - 1);
  }

  private static boolean isEndValue(String json, char c, int i) {
    return (c == ',' && ((i + 2) < json.length() && (json.charAt(i + 1) == '\"')) );
  }

  private static boolean isEndKey(String json, char c, int i) {
    return c == '\"' && ((i + 2) < json.length() && (json.charAt(i + 1) == ':'));
  }

  @Override
  public <T> T mapStringJsonToObject(String value, Field field) {
    return null;
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

  private Class<?> getClass(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new SerializationException(e);
    }
  }

  private List<Type> getParameterizedTypes(Field field) {
    Type fieldType = field.getGenericType();
    return fieldType instanceof ParameterizedType parameterizedType
        ? List.of(parameterizedType.getActualTypeArguments())
        : Collections.emptyList();
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
  private Field getField(String key, Class<?> tClass) {
    return Arrays.stream(tClass.getDeclaredFields())
        .peek(field -> field.setAccessible(true))
        .filter(field -> field.getName().equals(key))
        .findFirst()
        .orElseThrow();
  }

  @SuppressWarnings({"java:S3864", "java:S3011"})
  private Class<?> getFieldClass(String key, Class<?> tClass) {
    try {
      return getField(key, tClass).getType();
    } catch (NoSuchElementException e) {
      return Object.class;
    }
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

  private boolean isExistParameterizedGenericType(Field field) {
    Type fieldType = field.getGenericType();
    return fieldType instanceof ParameterizedType;
  }
}
