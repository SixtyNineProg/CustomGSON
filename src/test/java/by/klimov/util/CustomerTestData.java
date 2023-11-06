package by.klimov.util;

import by.klimov.dto.Customer;
import by.klimov.dto.Order;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with", toBuilder = true)
public class CustomerTestData {

  @Builder.Default private UUID id = UUID.fromString("097ceff8-27a8-4b31-a019-5069ea80ab5b");
  @Builder.Default private String firstName = "Dzmitry";
  @Builder.Default private String lastName = "Klimov";
  @Builder.Default private LocalDate dateBirth = LocalDate.of(2020, Month.JANUARY, 8);
  @Builder.Default private List<Order> orders = OrderTestData.builder().build().buildOrders();

  public Customer buildCustomer() {
    return Customer.builder()
        .id(id)
        .firstName(firstName)
        .lastName(lastName)
        .dateBirth(dateBirth)
        .orders(orders)
        .build();
  }

  private List<Customer> buildCustomers() {
    return List.of(
        CustomerTestData.builder().build().buildCustomer(),
        CustomerTestData.builder().build().buildCustomer(),
        CustomerTestData.builder().build().buildCustomer());
  }
}
