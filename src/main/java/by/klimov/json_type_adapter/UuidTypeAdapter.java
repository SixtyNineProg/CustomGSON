package by.klimov.json_type_adapter;

import static by.klimov.util.StringLiteral.DOUBLE_QUOTE;

import by.klimov.util.StringUtil;
import java.util.UUID;

public class UuidTypeAdapter implements BaseTypeAdapter {
  @Override
  public <T> boolean isAssignable(T object) {
    return object instanceof UUID;
  }

  @Override
  public boolean isAssignable(String value) {
    return isUuid(value);
  }

  @Override
  public UUID mapStringJsonToObject(String value) {
    return UUID.fromString(StringUtil.removeQuotes(value));
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
