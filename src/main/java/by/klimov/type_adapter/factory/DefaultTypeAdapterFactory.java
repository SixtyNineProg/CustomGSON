package by.klimov.type_adapter.factory;

import by.klimov.type_adapter.BaseTypeAdapter;
import by.klimov.type_adapter.BooleanTypeAdapter;
import by.klimov.type_adapter.DateTypeAdapter;
import by.klimov.type_adapter.DoubleTypeAdapter;
import by.klimov.type_adapter.LocalDateTimeTypeAdapter;
import by.klimov.type_adapter.StringTypeAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultTypeAdapterFactory implements TypeAdapterFactory {

  private final List<BaseTypeAdapter> typeAdapters;

  public DefaultTypeAdapterFactory() {
    this.typeAdapters = new ArrayList<>();
    typeAdapters.addAll(
        Arrays.asList(
            new BooleanTypeAdapter(),
            new DateTypeAdapter(),
            new LocalDateTimeTypeAdapter(),
            new DoubleTypeAdapter(),
            new StringTypeAdapter()));
  }

  @Override
  public <T> BaseTypeAdapter getTypeAdapter(T object) {
    return typeAdapters.stream()
        .filter(typeAdapter -> isAssignableFrom(object))
        .findFirst()
        .orElseThrow();
  }

  private <T> boolean isAssignableFrom(T object) {
    return typeAdapters.stream().anyMatch(type -> type.isAssignable(object));
  }
}
