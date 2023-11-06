package by.klimov.type_adapter.factory;

import by.klimov.type_adapter.BaseTypeAdapter;
import by.klimov.type_adapter.BooleanTypeAdapter;
import by.klimov.type_adapter.DateTypeAdapter;
import by.klimov.type_adapter.DoubleTypeAdapter;
import by.klimov.type_adapter.IntegerTypeAdapter;
import by.klimov.type_adapter.LocalDateTimeTypeAdapter;
import by.klimov.type_adapter.StringTypeAdapter;

import java.lang.reflect.Type;
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
            new DoubleTypeAdapter(),
            new IntegerTypeAdapter(),
            new LocalDateTimeTypeAdapter(),
            new StringTypeAdapter()));
  }

  public DefaultTypeAdapterFactory(List<BaseTypeAdapter> adapters) {
    this();
    this.typeAdapters.addAll(0, adapters);
  }

  @Override
  public BaseTypeAdapter getTypeAdapter(Type type) {
    return typeAdapters.stream()
        .filter(typeAdapter -> typeAdapter.isAssignableFrom(type))
        .findFirst().orElseThrow();
  }
}
