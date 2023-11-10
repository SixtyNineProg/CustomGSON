package by.klimov.json_type_adapter;

import static by.klimov.util.StringLiteral.DOUBLE_QUOTE;

import by.klimov.util.Constant;
import by.klimov.util.StringUtil;
import java.util.Objects;

public class StringTypeAdapter implements BaseTypeAdapter {

  @Override
  public <T> boolean isAssignable(T object) {
    return object instanceof String;
  }

  @Override
  public boolean isAssignable(String value) {
    return value.startsWith("\"");
  }

  @SuppressWarnings("unchecked")
  @Override
  public String mapStringJsonToObject(String value) {
    String extractedValue = StringUtil.extractString(value, Constant.STRING_REGEX);
    return Objects.isNull(extractedValue) ? value : extractedValue;
  }

  @Override
  public <T> StringBuilder mapObjectToStringJson(T object) {
    return new StringBuilder(DOUBLE_QUOTE + object + DOUBLE_QUOTE);
  }
}
