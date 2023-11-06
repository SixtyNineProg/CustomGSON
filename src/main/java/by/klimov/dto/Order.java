package by.klimov.dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {

  private UUID id;
  private List<Product> products;
  private OffsetDateTime createDate;
}
