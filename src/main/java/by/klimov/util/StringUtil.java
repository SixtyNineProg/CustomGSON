package by.klimov.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {

  public static String removeQuotes(String string) {
    return string.replace("\"", "");
  }
}
