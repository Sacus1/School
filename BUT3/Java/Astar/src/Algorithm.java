public interface Algorithm {
  void findPath(Vertex start, Vertex end) throws NoPathFoundException;

  default void drawVertex(Vertex vertex, Model model) {
    if (vertex.type != Type.DEPART && vertex.type != Type.END) vertex.type = Type.CURRENT;
    try {
      Thread.sleep(model.waitTime);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    if (vertex.type != Type.DEPART && vertex.type != Type.END) vertex.type = Type.EXPLORED;
  }
}
