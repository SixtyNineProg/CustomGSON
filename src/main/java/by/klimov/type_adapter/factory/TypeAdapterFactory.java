package by.klimov.type_adapter.factory;

import by.klimov.type_adapter.BaseTypeAdapter;

import java.lang.reflect.Type;

public interface TypeAdapterFactory {

  BaseTypeAdapter getTypeAdapter(Type type);
}
