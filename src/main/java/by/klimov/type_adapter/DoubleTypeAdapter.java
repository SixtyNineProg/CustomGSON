package by.klimov.type_adapter;

public class DoubleTypeAdapter extends BaseTypeAdapter{
    @Override
    public <T> T mapJsonToObject(String json, Class<T> tClass) {
        return null;
    }

    @Override
    public <T> String mapObjectToJson(T object) {
        return null;
    }
}
