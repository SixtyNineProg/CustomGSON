package by.klimov.json_type_adapter.factory;

import by.klimov.json_type_adapter.BaseTypeAdapter;

public interface TypeAdapterFactory {

  <T> BaseTypeAdapter getTypeAdapter(T object);
}
