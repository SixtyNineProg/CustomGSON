package by.klimov.type_adapter;

import by.klimov.type_adapter.factory.TypeAdapterFactory;
import by.klimov.type_adapter.factory.TypeAdapterFactoryImpl;
import java.lang.reflect.Field;
import org.apache.commons.lang3.SerializationException;

public class DefaultTypeAdapter implements BaseTypeAdapter {

  private final TypeAdapterFactory typeAdapterFactory = new TypeAdapterFactoryImpl();

  @Override
  public <T> boolean isAssignable(T object) {
    return true;
  }

  @Override
  public <T> T mapStringJsonToObject(String json, Class<T> tClass) {
    return null;
  }

  @SuppressWarnings("java:S3011")
  @Override
  public <T> StringBuilder mapObjectToStringJson(T object) {
    StringBuilder sb = new StringBuilder("{");
    for (Field field : object.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      BaseTypeAdapter baseTypeAdapter = typeAdapterFactory.getTypeAdapter(object);
      try {
        sb.append("\"")
            .append(field.getName())
            .append("\":")
            .append(baseTypeAdapter.mapObjectToStringJson(field.get(object)))
            .append(",");
      } catch (IllegalAccessException e) {
        throw new SerializationException(e);
      }
    }
    sb.append("}");
    return sb;
  }
}
