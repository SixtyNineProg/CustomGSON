package by.klimov.service.impl;

import by.klimov.service.JsonService;
import by.klimov.type_adapter.BaseTypeAdapter;
import by.klimov.type_adapter.factory.TypeAdapterFactory;
import by.klimov.type_adapter.factory.TypeAdapterFactoryImpl;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class JsonServiceImpl implements JsonService {

  private final TypeAdapterFactory typeAdapterFactory = new TypeAdapterFactoryImpl();

  @Override
  public <T> T mapJsonToObject(String json, Class<T> tClass) {
    return null;
  }

  @Override
  public <T> String mapObjectToJson(T object) {
    BaseTypeAdapter baseTypeAdapter = typeAdapterFactory.getTypeAdapter(object);
    return baseTypeAdapter.mapObjectToStringJson(object).toString();
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
