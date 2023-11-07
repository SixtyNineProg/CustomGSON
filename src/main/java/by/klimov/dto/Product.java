package by.klimov.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

  private UUID id;
  private String name;
  private Double price;
  private Boolean isEnable;
}
