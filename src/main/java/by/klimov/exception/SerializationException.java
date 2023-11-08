package by.klimov.exception;

public class SerializationException extends RuntimeException {

  private static final String DEFAULT_ERROR_MESSAGE = "Serialization exception";

  public SerializationException(Exception e) {
    super(DEFAULT_ERROR_MESSAGE, e);
  }
}
