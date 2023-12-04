import java.util.ArrayList;

public class BreadthFirst implements Algorithm {

  public void findPath(Vertex source, Vertex destination) throws NoPathFoundException {
    ArrayList<Vertex> frontier;
    ArrayList<Vertex> reached;
    frontier = new ArrayList<>();
    reached = new ArrayList<>();
    frontier.add(source);
    while (true) {
      if (frontier.isEmpty()) {
        throw new NoPathFoundException();
      }
      Vertex current = frontier.remove(0);
      drawVertex(current);
      for (Vertex neighbor : model.getAdjacent(current, reached)) {
        if (!reached.contains(neighbor)) {
          frontier.add(neighbor);
          reached.add(neighbor);
          neighbor.setPrevious((current));
          if (neighbor.equals(destination)) {
            return;
          }
        }
      }
    }
  }
}
