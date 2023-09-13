import java.util.ArrayList;

public class BreadthFirst implements Algorithm {

  Model model;
  private ArrayList<Vertex> frontier;
  private ArrayList<Vertex> reached;

  public BreadthFirst() {
    this.model = Model.getInstance();
  }

  public void findPath(Vertex source, Vertex destination) throws NoPathFoundException {
    frontier = new ArrayList<>();
    reached = new ArrayList<>();
    frontier.add(source);
    while (true) {
      if (frontier.isEmpty()) {
        throw new NoPathFoundException();
      }
      Vertex current = frontier.remove(0);
      drawVertex(current, model);
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
