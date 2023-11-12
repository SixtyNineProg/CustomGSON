package by.klimov.json_type_adapter;

import static by.klimov.util.StringLiteral.COLON;
import static by.klimov.util.StringLiteral.COMMA;
import static by.klimov.util.StringLiteral.LEFT_BRACE;
import static by.klimov.util.StringLiteral.RIGHT_BRACE;

import by.klimov.json_type_adapter.factory.TypeAdapterFactory;
import by.klimov.json_type_adapter.factory.impl.TypeAdapterFactoryImpl;
import by.klimov.util.JsonParser;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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

  @SuppressWarnings("unchecked")
  @Override
  public <T> T mapStringJsonToObject(String json, Class<T> tClass) {
    TypeAdapterFactory typeAdapterFactory = new TypeAdapterFactoryImpl();
    Map<String, Object> map = new HashMap<>();
    Map<String, String> jsonData = JsonParser.parseJson(json);
    for (Map.Entry<String, String> jsonObject : jsonData.entrySet()) {
      String key = jsonObject.getKey();
      String value = jsonObject.getValue();
      BaseTypeAdapter typeAdapter = typeAdapterFactory.getTypeAdapter(value);
      map.put(key, typeAdapter.mapStringJsonToObject(value, typeAdapter.getClassType()));
    }
    return (T) map;
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

  @Override
  public Class<?> getClassType() {
    return Map.class;
  }
}
