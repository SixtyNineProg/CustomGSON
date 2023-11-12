package by.klimov.json_type_adapter;

import static by.klimov.util.StringLiteral.COLON;
import static by.klimov.util.StringLiteral.COMMA;
import static by.klimov.util.StringLiteral.DOUBLE_QUOTE;
import static by.klimov.util.StringLiteral.LEFT_BRACE;
import static by.klimov.util.StringLiteral.RIGHT_BRACE;

import by.klimov.json_type_adapter.factory.TypeAdapterFactory;
import by.klimov.json_type_adapter.factory.impl.TypeAdapterFactoryImpl;
import by.klimov.util.JsonParser;
import by.klimov.util.ReflectionUtil;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
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
      Map<String, String> jsonData = JsonParser.parseJson(json);
      for (Map.Entry<String, String> jsonObject : jsonData.entrySet()) {
        String key = jsonObject.getKey();
        String value = jsonObject.getValue();
        Field field = ReflectionUtil.getField(key, tClass);
        if (ReflectionUtil.isExistParameterizedGenericType(field)) {
          Type fieldType = field.getGenericType();
          ParameterizedType type = (ParameterizedType) fieldType;
          List<Type> actualTypeArguments = List.of(type.getActualTypeArguments());
          Class<?> rawTypeClass = ReflectionUtil.getClass(type.getRawType().getTypeName());
          CollectionBaseTypeAdapter typeAdapter =
              typeAdapterFactory.getCollectionTypeAdapter(rawTypeClass);
          map.put(key, typeAdapter.mapStringJsonToObject(value, actualTypeArguments));
        } else {
          Class<?> fieldClass = field.getType();
          BaseTypeAdapter typeAdapter = typeAdapterFactory.getTypeAdapter(value, fieldClass);
          map.put(key, typeAdapter.mapStringJsonToObject(value, fieldClass));
        }
      }
      return ReflectionUtil.buildObject(tClass, map);
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
      Object fieldObject = ReflectionUtil.getFieldObject(object, field);
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

  @Override
  public Class<?> getClassType() {
    return Object.class;
  }
}
