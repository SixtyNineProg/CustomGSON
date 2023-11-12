package by.klimov.json_type_adapter;

import static by.klimov.util.StringLiteral.COMMA;
import static by.klimov.util.StringLiteral.LEFT_BRACKET;
import static by.klimov.util.StringLiteral.RIGHT_BRACKET;

import by.klimov.exception.SerializationException;
import by.klimov.json_type_adapter.factory.TypeAdapterFactory;
import by.klimov.json_type_adapter.factory.impl.TypeAdapterFactoryImpl;
import by.klimov.util.StringLiteral;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListTypeAdapter implements BaseTypeAdapter, CollectionBaseTypeAdapter {

  @Override
  public <T> boolean isAssignable(T object) {
    return object instanceof List<?>;
  }

  @Override
  public boolean isAssignable(String value) {
    return value.startsWith(LEFT_BRACKET) && value.endsWith(RIGHT_BRACKET);
  }

  @Override
  public <T> boolean isAssignable(Class<T> tClass) {
    return tClass.equals(List.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T mapStringJsonToObject(String json, Class<T> tClass) {
    TypeAdapterFactory typeAdapterFactory = new TypeAdapterFactoryImpl();
    Pattern pattern = Pattern.compile("\\{(?:[^{}]|\\{(?:[^{}]|\\{[^{}]*\\})*\\})*\\}");
    Matcher matcher = pattern.matcher(json);
    List<Object> list = new ArrayList<>();
    while (matcher.find()) {
      String element = matcher.group();
      BaseTypeAdapter typeAdapter = typeAdapterFactory.getTypeAdapter(element, Object.class);
      list.add(typeAdapter.mapStringJsonToObject(element, Object.class));
    }
    return (T) list;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> StringBuilder mapObjectToStringJson(T object) {
    StringBuilder stringBuilder = new StringBuilder(LEFT_BRACKET);
    List<Objects> objectsList = (List<Objects>) object;
    for (Iterator<Objects> iterator = objectsList.iterator(); iterator.hasNext(); ) {
      Object iterObject = iterator.next();
      TypeAdapterFactory typeAdapterFactory = new TypeAdapterFactoryImpl();
      BaseTypeAdapter baseTypeAdapter = typeAdapterFactory.getTypeAdapter(iterObject);
      stringBuilder.append(baseTypeAdapter.mapObjectToStringJson(iterObject));
      if (iterator.hasNext()) {
        stringBuilder.append(COMMA);
      }
    }
    stringBuilder.append(StringLiteral.RIGHT_BRACKET);
    return stringBuilder;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T mapStringJsonToObject(String value, List<Type> actualTypeArguments) {
    TypeAdapterFactory typeAdapterFactory = new TypeAdapterFactoryImpl();
    Pattern pattern = Pattern.compile("\\{(?:[^{}]|\\{(?:[^{}]|\\{[^{}]*\\})*\\})*\\}");
    Matcher matcher = pattern.matcher(value);
    List<Object> list = new ArrayList<>();
    while (matcher.find()) {
      String element = matcher.group();
      Class<?> rawTypeClass;
      List<Type> typeArguments = Collections.emptyList();
      try{
        ParameterizedType type = (ParameterizedType) actualTypeArguments.get(0);
        typeArguments = List.of(type.getActualTypeArguments());
        rawTypeClass = getClass(type.getRawType().getTypeName());
      } catch (ClassCastException e) {
        rawTypeClass = getClass(actualTypeArguments.get(0).getTypeName());
      }
      if (typeArguments.isEmpty()) {
        BaseTypeAdapter typeAdapter = typeAdapterFactory.getTypeAdapter(element, rawTypeClass);
        list.add(typeAdapter.mapStringJsonToObject(element, rawTypeClass));
      } else {
        CollectionBaseTypeAdapter typeAdapter = typeAdapterFactory.getCollectionTypeAdapter(rawTypeClass);
        list.add(typeAdapter.mapStringJsonToObject(element, typeArguments));
      }
    }
    return (T) list;
  }

  private Class<?> getClass(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new SerializationException(e);
    }
  }
}
