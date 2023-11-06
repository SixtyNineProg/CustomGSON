package by.klimov.service.impl;

import by.klimov.service.JsonService;
import by.klimov.type_adapter.BaseTypeAdapter;
import by.klimov.type_adapter.factory.DefaultTypeAdapterFactory;
import by.klimov.type_adapter.factory.TypeAdapterFactory;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public class JsonServiceImpl implements JsonService {

  public static final String STRING_NULL = "null";

  private final TypeAdapterFactory typeAdapterFactory = new DefaultTypeAdapterFactory();

  @Override
  public <T> T mapJsonToObject(String json, Class<T> tClass) {
    return null;
  }

  @Override
  public <T> String mapObjectToJson(T object) {
//    if (Objects.isNull(object)) {
//      return STRING_NULL;
//    }
    if (object instanceof String) {
      return "\"" + object + "\"";
    }
    BaseTypeAdapter baseTypeAdapter = typeAdapterFactory.getTypeAdapter(object);
    return baseTypeAdapter.mapObjectToStringJson(object);
  }

  @Override
  public <T> List<T> mapJsonToObjects(String json, Class<T>[] tClass) {
    return null;
  }

  @Override
  public <T> String mapObjectsToJson(List<T> objects) {
    if (objects.isEmpty()) {
      return StringUtils.EMPTY;
    }
    return StringUtils.EMPTY;
  }
}
