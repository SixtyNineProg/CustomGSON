package by.klimov.json_type_adapter.factory;

import by.klimov.json_type_adapter.BaseTypeAdapter;
import by.klimov.json_type_adapter.BooleanTypeAdapter;
import by.klimov.json_type_adapter.ObjectTypeAdapter;
import by.klimov.json_type_adapter.DoubleTypeAdapter;
import by.klimov.json_type_adapter.ListTypeAdapter;
import by.klimov.json_type_adapter.LocalDateTypeAdapter;
import by.klimov.json_type_adapter.MapTypeAdapter;
import by.klimov.json_type_adapter.NullTypeAdapter;
import by.klimov.json_type_adapter.StringTypeAdapter;
import by.klimov.json_type_adapter.UuidTypeAdapter;
import by.klimov.json_type_adapter.ZonedDateTimeTypeAdapter;
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
            new BooleanTypeAdapter(),
            new DoubleTypeAdapter(),
            new UuidTypeAdapter(),
            new StringTypeAdapter(),
            new LocalDateTypeAdapter(),
            new ZonedDateTimeTypeAdapter(),
            new ListTypeAdapter(),
            new MapTypeAdapter()));
  }

  @Override
  public <T> BaseTypeAdapter getTypeAdapter(T object) {
    return Objects.isNull(object) ? new NullTypeAdapter() : getBaseTypeAdapter(object);
  }

  @Override
  public BaseTypeAdapter getTypeAdapter(String value) {
    return Objects.isNull(value) ? new NullTypeAdapter() : getBaseTypeAdapter(value);
  }

  private <T> BaseTypeAdapter getBaseTypeAdapter(T object) {
    return typeAdapters.stream()
        .filter(typeAdapter -> typeAdapter.isAssignable(object))
        .findFirst()
        .orElse(new ObjectTypeAdapter());
  }

  private BaseTypeAdapter getBaseTypeAdapter(String value) {
    return typeAdapters.stream()
        .filter(typeAdapter -> typeAdapter.isAssignable(value))
        .findFirst()
        .orElse(new StringTypeAdapter());
  }
}
