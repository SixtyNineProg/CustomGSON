package by.klimov.util;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with", toBuilder = true)
public class ListTestData {

  @Builder.Default private List<String> strings = List.of("one", "two", "three");
}
