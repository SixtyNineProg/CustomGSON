package by.klimov.type_adapter;

import by.klimov.type_adapter.factory.TypeAdapterFactory;
import by.klimov.type_adapter.factory.TypeAdapterFactoryImpl;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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
    List<Field> fields = Arrays.stream(object.getClass().getDeclaredFields()).toList();
    for (Iterator<Field> iterator = fields.iterator(); iterator.hasNext(); ) {
      Field field = iterator.next();
      field.setAccessible(true);
      Object fieldObject = getFieldObject(object, field);
      BaseTypeAdapter baseTypeAdapter = typeAdapterFactory.getTypeAdapter(fieldObject);
      sb.append("\"")
          .append(field.getName())
          .append("\":")
          .append(baseTypeAdapter.mapObjectToStringJson(fieldObject));
      if (iterator.hasNext()) {
        sb.append(",");
      }
    }
    sb.append("}");
    return sb;
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
}
