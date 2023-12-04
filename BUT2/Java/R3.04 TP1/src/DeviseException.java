public class DeviseException extends Exception {
  public DeviseException(String expected, String actual) {
    super("DeviseException: expected " + expected + " but was " + actual);
  }
}
