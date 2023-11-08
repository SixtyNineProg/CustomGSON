package by.klimov.dto;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {

  private UUID id;
  private String firstName;
  private String lastName;
  private ZonedDateTime dateBirth;
  private List<Order> orders;
}
