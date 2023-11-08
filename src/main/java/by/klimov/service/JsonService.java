package by.klimov.service;

public interface JsonService {

  <T> T mapJsonToObject(String json, Class<T> tClass);

  <T> String mapObjectToJson(T object);
}
