package by.klimov.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import by.klimov.dto.Customer;
import by.klimov.dto.Order;
import by.klimov.dto.Product;
import by.klimov.gson_type_adapter.LocalDateTypeAdapter;
import by.klimov.gson_type_adapter.ZonedDateTimeTypeAdapter;
import by.klimov.util.CustomerTestData;
import by.klimov.util.ListTestData;
import by.klimov.util.MapTestData;
import by.klimov.util.OrderTestData;
import by.klimov.util.ProductTestData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

class JsonServiceImplTest {

  private final Gson gson =
      new GsonBuilder()
          .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
          .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
          .create();
  private final JsonServiceImpl jsonService = new JsonServiceImpl();

  @Test
  void mapJsonToObject_whenJsonStringToProduct_thenProductExpected() {
    // Given
    Product expected = ProductTestData.builder().build().buildProduct();
    String json = gson.toJson(expected);

    // When
    Product actual = jsonService.mapJsonToObject(json, Product.class);

    // Then
    assertThat(actual)
        .hasFieldOrPropertyWithValue(Product.Fields.name, expected.getName())
        .hasFieldOrPropertyWithValue(Product.Fields.id, expected.getId())
        .hasFieldOrPropertyWithValue(Product.Fields.isEnable, expected.getIsEnable())
        .hasFieldOrPropertyWithValue(Product.Fields.price, expected.getPrice());
  }

  @Test
  void mapObjectToJson_whenProductToJson_thenValidJsonExpected() throws JSONException {
    // Given
    Product product = ProductTestData.builder().build().buildProduct();
    String expected = gson.toJson(product);

    // When
    String actual = jsonService.mapObjectToJson(product);

    // Then
    JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT);
  }

  @Test
  void mapObjectToJson_whenOrderToJson_thenValidJsonExpected() throws JSONException {
    // Given
    Order order = OrderTestData.builder().build().buildOrder();
    String expected = gson.toJson(order);

    // When
    String actual = jsonService.mapObjectToJson(order);

    // Then
    JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT);
  }

  @Test
  void mapObjectToJson_whenCustomerToJson_thenValidJsonExpected() throws JSONException {
    // Given
    Customer customer = CustomerTestData.builder().build().buildCustomer();
    String expected = gson.toJson(customer);

    // When
    String actual = jsonService.mapObjectToJson(customer);

    // Then
    JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT);
  }

  @Test
  void mapObjectToJson_whenIsNull_thenEmptyStringExpected() {
    // Given
    String expected = gson.toJson(null);

    // When
    String actual = jsonService.mapObjectToJson(null);

    // Then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void mapJsonToObject_whenInputEmptyString_thenEmptyStringExpected() {
    // Given
    Product expected = gson.fromJson(StringUtils.EMPTY, Product.class);

    // When
    Product actual = jsonService.mapJsonToObject(StringUtils.EMPTY, Product.class);

    // Then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void mapObjectToJson_whenInputString_thenShieldStringExpected() {
    // Given
    String name = ProductTestData.builder().build().getName();
    String expected = gson.toJson(name);

    // When
    String actual = jsonService.mapObjectToJson(name);

    // Then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void mapJsonToObject_whenInputString_thenStringExpected() {
    // Given
    String name = ProductTestData.builder().build().getName();
    String expected = gson.fromJson(name, String.class);

    // When
    String actual = jsonService.mapJsonToObject(name, String.class);

    // Then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void mapObjectToJson_whenInputDouble_thenStringFromDoubleExpected() {
    // Given
    Double price = ProductTestData.builder().build().getPrice();
    String expected = gson.toJson(price);

    // When
    String actual = jsonService.mapObjectToJson(price);

    // Then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void mapJsonToObject_whenInputDoubleString_thenDoubleExpected() {
    // Given
    String price = gson.toJson(ProductTestData.builder().build().getPrice());
    Double expected = gson.fromJson(price, Double.class);

    // When
    Double actual = jsonService.mapJsonToObject(price, Double.class);

    // Then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void mapObjectToJson_whenInputBoolean_thenStringFromBooleanExpected() {
    // Given
    Boolean isEnable = ProductTestData.builder().build().getIsEnable();
    String expected = gson.toJson(isEnable);

    // When
    String actual = jsonService.mapObjectToJson(isEnable);

    // Then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void mapJsonToObject_whenInputBooleanString_thenBooleanExpected() {
    // Given
    String isEnable = gson.toJson(ProductTestData.builder().build().getIsEnable());
    Boolean expected = gson.fromJson(isEnable, Boolean.class);

    // When
    Boolean actual = jsonService.mapJsonToObject(isEnable, Boolean.class);

    // Then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void mapObjectToJson_whenInputUuid_thenStringFromUuidExpected() {
    // Given
    UUID uuid = ProductTestData.builder().build().getUuid();
    String expected = gson.toJson(uuid);

    // When
    String actual = jsonService.mapObjectToJson(uuid);

    // Then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void mapJsonToObject_whenInputUuidString_thenUuidExpected() {
    // Given
    String uuid = gson.toJson(ProductTestData.builder().build().getUuid());
    UUID expected = gson.fromJson(uuid, UUID.class);

    // When
    UUID actual = jsonService.mapJsonToObject(uuid, UUID.class);

    // Then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void mapObjectToJson_whenInputLocalDate_thenStringFromLocalDateExpected() {
    // Given
    LocalDate localDate = OrderTestData.builder().build().getCreateDate();
    String expected = gson.toJson(localDate);

    // When
    String actual = jsonService.mapObjectToJson(localDate);

    // Then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void mapJsonToObject_whenInputLocalDateString_thenLocalDateExpected() {
    // Given
    String localDate = gson.toJson(OrderTestData.builder().build().getCreateDate());
    LocalDate expected = gson.fromJson(localDate, LocalDate.class);

    // When
    LocalDate actual = jsonService.mapJsonToObject(localDate, LocalDate.class);

    // Then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void mapObjectToJson_whenInputZonedDataTime_thenStringFromZonedDataTimeExpected() {
    // Given
    ZonedDateTime zonedDateTime = CustomerTestData.builder().build().getDateBirth();
    String expected = gson.toJson(zonedDateTime);

    // When
    String actual = jsonService.mapObjectToJson(zonedDateTime);

    // Then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void mapJsonToObject_whenInputZonedDataTimeString_thenZonedDataTimeExpected() {
    // Given
    String zonedDateTime = gson.toJson(CustomerTestData.builder().build().getDateBirth());
    ZonedDateTime expected = gson.fromJson(zonedDateTime, ZonedDateTime.class);

    // When
    ZonedDateTime actual = jsonService.mapJsonToObject(zonedDateTime, ZonedDateTime.class);

    // Then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void mapObjectToJson_whenInputStringsList_thenStringFromStringsListExpected() {
    // Given
    List<String> strings = ListTestData.builder().build().getStrings();
    String expected = gson.toJson(strings);

    // When
    String actual = jsonService.mapObjectToJson(strings);

    // Then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void mapObjectToJson_whenInputCustomersList_thenStringFromCustomersListExpected() {
    // Given
    List<Customer> customers = CustomerTestData.builder().build().buildCustomers();
    String expected = gson.toJson(customers);

    // When
    String actual = jsonService.mapObjectToJson(customers);

    // Then
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void mapObjectToJson_whenInputMapWithStringKeyAndStringValue_thenStringFromMapExpected() {
    // Given
    Map<String, String> map = MapTestData.builder().build().getMap();
    String expected = gson.toJson(map);

    // When
    String actual = jsonService.mapObjectToJson(map);

    // Then
    assertThat(actual).isEqualTo(expected);
  }

  @SuppressWarnings("unchecked")
  @Test
  void mapJsonToObject_whenInputJsonMapWithStringKeyAndStringValue_thenMapExpected() {
    // Given
    String map = gson.toJson(MapTestData.builder().build().getMap());
    Map<String, String> expected = gson.fromJson(map, Map.class);

    // When
    Map<String, String> actual = jsonService.mapJsonToObject(map, Map.class);

    // Then
    assertThat(actual).isEqualTo(expected);
  }
}
