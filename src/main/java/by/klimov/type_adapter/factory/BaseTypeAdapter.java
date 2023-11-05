package by.klimov.type_adapter.factory;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseTypeAdapter {
  private final List<Type> allowedTypes;

  protected BaseTypeAdapter(Type... types) {
    this.allowedTypes = new LinkedList<>();
    allowedTypes.addAll(Arrays.asList(types));
  }

  public boolean isAssignableFrom(Type clazz) {
    return allowedTypes.stream().anyMatch(type -> type.equals(clazz));
  }

  public abstract <T> T mapJsonToObject(String json, Class<T> tClass);

  public abstract <T> String mapObjectToJson(T object);
}
