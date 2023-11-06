package by.klimov.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import by.klimov.dto.Product;
import by.klimov.util.ProductTestData;
import com.google.gson.Gson;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

class JsonServiceImplTest {

  private final Gson gson = new Gson();
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
  void mapObjectToJson_whenInputNumber_thenStringFromNumberExpected() {
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
  void mapJsonToObjects_when_then() {
    // Given

    // When

    // Then
  }

  @Test
  void mapObjectsToJson_when_then() {
    // Given

    // When

    // Then
  }
}
