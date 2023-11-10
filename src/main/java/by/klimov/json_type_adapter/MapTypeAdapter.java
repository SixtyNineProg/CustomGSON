package by.klimov.json_type_adapter;

import static by.klimov.util.StringLiteral.COLON;
import static by.klimov.util.StringLiteral.COMMA;
import static by.klimov.util.StringLiteral.LEFT_BRACE;
import static by.klimov.util.StringLiteral.RIGHT_BRACE;

import by.klimov.json_type_adapter.factory.TypeAdapterFactory;
import by.klimov.json_type_adapter.factory.impl.TypeAdapterFactoryImpl;
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

  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> mapStringJsonToObject(String json) {
    TypeAdapterFactory typeAdapterFactory = new TypeAdapterFactoryImpl();
    Map<String, Object> map = new HashMap<>();
    Pattern pattern =
        Pattern.compile("\"(.*?)\":\\s*(\".*?\"|\\d*[.]?\\d+|true|false|\\{.*?\\}|\\[.*?\\])");
    Matcher matcher = pattern.matcher(json);
    while (matcher.find()) {
      String key = matcher.group(1);
      String value = matcher.group(2);
      BaseTypeAdapter typeAdapter = typeAdapterFactory.getTypeAdapter(value);
      map.put(key, typeAdapter.mapStringJsonToObject(value));
    }
    return map;
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
}
