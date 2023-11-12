package by.klimov.service.impl;

import by.klimov.json_type_adapter.BaseTypeAdapter;
import by.klimov.json_type_adapter.factory.TypeAdapterFactory;
import by.klimov.json_type_adapter.factory.impl.TypeAdapterFactoryImpl;
import by.klimov.service.JsonService;

public class JsonServiceImpl implements JsonService {

  private final TypeAdapterFactory typeAdapterFactory = new TypeAdapterFactoryImpl();

  @Override
  public <T> T mapJsonToObject(String json, Class<T> tClass) {
    BaseTypeAdapter baseTypeAdapter = typeAdapterFactory.getTypeAdapter(json, tClass);
    return baseTypeAdapter.mapStringJsonToObject(json, tClass);
  }

  @Override
  public <T> String mapObjectToJson(T object) {
    BaseTypeAdapter baseTypeAdapter = typeAdapterFactory.getTypeAdapter(object);
    return baseTypeAdapter.mapObjectToStringJson(object).toString();
  }
}
