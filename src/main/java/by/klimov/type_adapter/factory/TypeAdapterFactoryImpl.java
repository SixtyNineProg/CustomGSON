package by.klimov.type_adapter.factory;

import by.klimov.type_adapter.BaseTypeAdapter;
import by.klimov.type_adapter.BooleanTypeAdapter;
import by.klimov.type_adapter.DefaultTypeAdapter;
import by.klimov.type_adapter.DoubleTypeAdapter;
import by.klimov.type_adapter.NullTypeAdapter;
import by.klimov.type_adapter.UuidTypeAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TypeAdapterFactoryImpl implements TypeAdapterFactory {

  private final List<BaseTypeAdapter> typeAdapters;

  public TypeAdapterFactoryImpl() {
    this.typeAdapters = new ArrayList<>();
    typeAdapters.addAll(
        Arrays.asList(
//            new BooleanTypeAdapter(),
//            new DoubleTypeAdapter(),
            new UuidTypeAdapter()));
  }

  @Override
  public <T> BaseTypeAdapter getTypeAdapter(T object) {
    return Objects.isNull(object)
        ? new NullTypeAdapter()
        : typeAdapters.stream()
            .filter(typeAdapter -> isAssignableFrom(object))
            .findFirst()
            .orElse(new DefaultTypeAdapter());
  }

  private <T> boolean isAssignableFrom(T object) {
    return typeAdapters.stream().anyMatch(type -> type.isAssignable(object));
  }
}
