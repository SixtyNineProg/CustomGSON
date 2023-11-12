package by.klimov.json_type_adapter;

import java.lang.reflect.Type;
import java.util.List;

public interface CollectionBaseTypeAdapter {

    <T> T mapStringJsonToObject(String value, List<Type> actualTypeArguments);
    <T> boolean isAssignable(Class<T> tClass);
}
