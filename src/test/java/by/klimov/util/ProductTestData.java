package by.klimov.util;

import by.klimov.dto.Product;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder(setterPrefix = "with", toBuilder = true)
@Accessors(chain = true)
public class ProductTestData {

  @Builder.Default private UUID uuid = UUID.fromString("097ceff8-27a8-4b31-a019-5069ea80ab5b");

  @Builder.Default private String name = ",laptop";

  @Builder.Default private Double price = 10000.0;

  @Builder.Default private Boolean isEnable = true;

  public Product buildProduct() {
    return Product.builder().id(uuid).name(name).price(price).isEnable(isEnable).build();
  }

  public List<Product> buildProducts() {
    return List.of(
        ProductTestData.builder().build().buildProduct(),
        ProductTestData.builder().build().buildProduct());
  }
}
