public class EdgeNotFoundException extends Exception {
  public EdgeNotFoundException(Vertex source, Vertex destination) {
    super("No edge between " + source + " and " + destination);
  }
}
