package by.klimov.json_type_adapter;

import static by.klimov.util.StringLiteral.COMMA;
import static by.klimov.util.StringLiteral.LEFT_BRACKET;

import by.klimov.json_type_adapter.factory.TypeAdapterFactory;
import by.klimov.json_type_adapter.factory.TypeAdapterFactoryImpl;
import by.klimov.util.StringLiteral;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ListTypeAdapter implements BaseTypeAdapter {

  @Override
  public <T> boolean isAssignable(T object) {
    return object instanceof List<?>;
  }

  @Override
  public <T> T mapStringJsonToObject(String json, Class<T> tClass) {
    return null;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> StringBuilder mapObjectToStringJson(T object) {
    StringBuilder sb = new StringBuilder(LEFT_BRACKET);
    List<Objects> objectsList = (List<Objects>) object;
    for (Iterator<Objects> iterator = objectsList.iterator(); iterator.hasNext(); ) {
      Object iterObject = iterator.next();
      TypeAdapterFactory typeAdapterFactory = new TypeAdapterFactoryImpl();
      BaseTypeAdapter baseTypeAdapter = typeAdapterFactory.getTypeAdapter(iterObject);
      sb.append(baseTypeAdapter.mapObjectToStringJson(iterObject));
      if (iterator.hasNext()) {
        sb.append(COMMA);
      }
    }
    sb.append(StringLiteral.RIGHT_BRACKET);
    return sb;
  }
}
