package by.klimov.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import by.klimov.dto.Order;
import by.klimov.dto.Product;
import by.klimov.gson_type_adapter.LocalDateTypeAdapter;
import by.klimov.util.ListTestData;
import by.klimov.util.OrderTestData;
import by.klimov.util.ProductTestData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

class JsonServiceImplTest {

  private final Gson gson =
      new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter()).create();
  private final JsonServiceImpl jsonService = new JsonServiceImpl();

  @Test
  void mapJsonToObject_when_then() {
    // Given

    // When

    // Then
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
  void mapObjectToJson_whenProductIsNull_thenEmptyStringExpected() {
    // Given
    Product product = null;
    String expected = gson.toJson(product);

    // When
    String actual = jsonService.mapObjectToJson(product);

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
  void mapObjectToJson_whenInputLocalDate_thenStringFromLocalDateTimeExpected() {
    // Given
    LocalDate localDate = OrderTestData.builder().build().getCreateDate();
    String expected = gson.toJson(localDate);

    // When
    String actual = jsonService.mapObjectToJson(localDate);

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
}
