package by.klimov.json_type_adapter.factory.impl;

import by.klimov.json_type_adapter.BaseTypeAdapter;
import by.klimov.json_type_adapter.BooleanTypeAdapter;
import by.klimov.json_type_adapter.CollectionBaseTypeAdapter;
import by.klimov.json_type_adapter.DoubleTypeAdapter;
import by.klimov.json_type_adapter.ListTypeAdapter;
import by.klimov.json_type_adapter.LocalDateTypeAdapter;
import by.klimov.json_type_adapter.MapTypeAdapter;
import by.klimov.json_type_adapter.NullTypeAdapter;
import by.klimov.json_type_adapter.ObjectTypeAdapter;
import by.klimov.json_type_adapter.StringTypeAdapter;
import by.klimov.json_type_adapter.UuidTypeAdapter;
import by.klimov.json_type_adapter.ZonedDateTimeTypeAdapter;
import by.klimov.json_type_adapter.factory.TypeAdapterFactory;
import java.util.List;
import java.util.Objects;

public class TypeAdapterFactoryImpl implements TypeAdapterFactory {

  private final List<BaseTypeAdapter> typeAdapters =
      List.of(
          new BooleanTypeAdapter(),
          new DoubleTypeAdapter(),
          new UuidTypeAdapter(),
          new StringTypeAdapter(),
          new LocalDateTypeAdapter(),
          new ZonedDateTimeTypeAdapter(),
          new ListTypeAdapter(),
          new MapTypeAdapter());

  private final List<CollectionBaseTypeAdapter> collectionBaseTypeAdapters =
      List.of(new ListTypeAdapter());

  @Override
  public BaseTypeAdapter getTypeAdapter(String value, Class<?> tClass) {
    return Objects.isNull(tClass) || value.isEmpty() || value.isBlank()
        ? new NullTypeAdapter()
        : getBaseTypeAdapter(value, tClass);
  }

  @Override
  public BaseTypeAdapter getTypeAdapter(Class<?> tClass) {
    return Objects.isNull(tClass) ? new NullTypeAdapter() : getBaseTypeAdapter(tClass);
  }

  @Override
  public CollectionBaseTypeAdapter getCollectionTypeAdapter(Class<?> tClass) {
    return collectionBaseTypeAdapters.stream()
        .filter(typeAdapter -> typeAdapter.isAssignable(tClass))
        .findFirst()
        .orElseThrow();
  }

  @Override
  public <T> BaseTypeAdapter getTypeAdapter(T object) {
    return Objects.isNull(object) ? new NullTypeAdapter() : getBaseTypeAdapter(object);
  }

  private <T> BaseTypeAdapter getBaseTypeAdapter(T object) {
    return typeAdapters.stream()
        .filter(typeAdapter -> typeAdapter.isAssignable(object))
        .findFirst()
        .orElse(new ObjectTypeAdapter());
  }

  private BaseTypeAdapter getBaseTypeAdapter(Class<?> tClass) {
    return typeAdapters.stream()
        .filter(typeAdapter -> typeAdapter.isAssignable(tClass))
        .findFirst()
        .orElseThrow();
  }

  private BaseTypeAdapter getBaseTypeAdapter(String value, Class<?> tClass) {
    return typeAdapters.stream()
        .filter(typeAdapter -> typeAdapter.isAssignable(tClass))
        .findFirst()
        .orElseGet(() -> getBaseTypeAdapter(value));
  }

  private BaseTypeAdapter getBaseTypeAdapter(String value) {
    BaseTypeAdapter nullTypeAdapter = new NullTypeAdapter();
    return nullTypeAdapter.isAssignable(value) ? nullTypeAdapter : new ObjectTypeAdapter();
  }
}
