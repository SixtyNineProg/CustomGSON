package by.klimov.util;

import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with", toBuilder = true)
public class MapTestData {

  @Builder.Default
  private Map<String, String> map =
      Map.of("name", "John", "city", "budapest", "zip", "000000", "home", "1231231231");
}
