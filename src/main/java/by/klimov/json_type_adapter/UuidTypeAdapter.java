package by.klimov.json_type_adapter;

import static by.klimov.util.StringLiteral.DOUBLE_QUOTE;

import by.klimov.util.Constant;
import by.klimov.util.StringUtil;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.UUID;

public class UuidTypeAdapter implements BaseTypeAdapter {
  @Override
  public <T> boolean isAssignable(T object) {
    return object instanceof UUID;
  }

  @Override
  public boolean isAssignable(String value) {
    String extractedValue = StringUtil.extractString(value, Constant.STRING_REGEX);
    return Objects.isNull(extractedValue) ? isUuid(value) : isUuid(extractedValue) || isUuid(value);
  }

  @Override
  public <T> boolean isAssignable(Class<T> tClass) {
    return tClass.equals(UUID.class);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T mapStringJsonToObject(String value, Class<T> tClass) {
    String extractedValue = StringUtil.extractString(value, Constant.STRING_REGEX);
    return (T)
        (Objects.isNull(extractedValue) ? UUID.fromString(value) : UUID.fromString(extractedValue));
  }

  @Override
  public <T> StringBuilder mapObjectToStringJson(T object) {
    return new StringBuilder(DOUBLE_QUOTE + object.toString() + DOUBLE_QUOTE);
  }

  private boolean isUuid(String string) {
    try {
      UUID.fromString(StringUtil.removeQuotes(string));
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }
}
