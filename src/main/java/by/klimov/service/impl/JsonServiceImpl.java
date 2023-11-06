package by.klimov.service.impl;

import by.klimov.service.JsonService;
import java.util.List;

public class JsonServiceImpl implements JsonService {

    @Override
    public <T> T mapJsonToObject(String json, Class<T> tClass) {
        return null;
    }

    @Override
    public <T> String mapObjectToJson(T object) {
        return null;
    }

    @Override
    public <T> List<T> mapJsonToObjects(String json, Class<T>[] tClass) {
        return null;
    }

    @Override
    public <T> String mapObjectsToJson(List<T> objects) {
        return null;
    }
}
