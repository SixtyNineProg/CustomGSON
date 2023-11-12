package by.klimov.dto;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@Builder
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

  private UUID id;
  private String firstName;
  private String lastName;
  private ZonedDateTime dateBirth;
  private List<Order> orders;
}
