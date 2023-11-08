package by.klimov.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {

  private UUID id;
  private List<Product> products;
  private LocalDate createDate;
}
