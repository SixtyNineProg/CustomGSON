package by.klimov.util;

import by.klimov.dto.Order;
import by.klimov.dto.Product;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with", toBuilder = true)
public class OrderTestData {

  @Builder.Default private UUID id = UUID.fromString("097ceff8-27a8-4b31-a019-5069ea80ab5b");

  @Builder.Default
  private List<Product> products = ProductTestData.builder().build().buildProducts();

  @Builder.Default
  private LocalDate createDate =
          LocalDate.of(2020, Month.JANUARY, 8);

  public Order buildOrder() {
    return Order.builder()
        .id(id)
        .products(ProductTestData.builder().build().buildProducts())
        .createDate(createDate)
        .build();
  }

  public List<Order> buildOrders() {
    return List.of(
        OrderTestData.builder().build().buildOrder(),
        OrderTestData.builder().build().buildOrder(),
        OrderTestData.builder().build().buildOrder());
  }
}
