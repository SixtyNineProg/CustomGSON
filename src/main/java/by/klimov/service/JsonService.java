package by.klimov.service;

import java.util.List;

public interface JsonService {
    <T> T mapJsonToObject(String json, Class<T> tClass);

    <T> String mapObjectToJson(T object);

    <T> List<T> mapJsonToObjects(String json, Class<T>[] tClass);

    <T> String mapObjectsToJson(List<T> objects);
}
