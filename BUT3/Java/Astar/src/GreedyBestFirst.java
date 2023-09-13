import java.util.*;

public class GreedyBestFirst implements Algorithm {
  Model model;
  private PriorityQueue<Vertex> frontier;
  private Map<Vertex, Vertex> cameFrom;

  public GreedyBestFirst() {
    this.model = Model.getInstance();
  }

  public void findPath(Vertex source, Vertex destination) throws NoPathFoundException {
    frontier = new PriorityQueue<>(Comparator.comparingInt(Vertex::getFCost));
    cameFrom = new HashMap<>();
    // source priority is 0
    frontier.add(source);
    while (true) {
      if (frontier.isEmpty()) {
        throw new NoPathFoundException();
      }
      Vertex current = frontier.poll();
      drawVertex(current, model);
      if (current.equals(destination)) {
        return;
      }
      for (Vertex neighbour : model.getAdjacent(current, null)) {
        if (!cameFrom.containsKey(neighbour)) {
          neighbour.setHCosts(destination);
          frontier.add(neighbour);
          cameFrom.put(neighbour, current);
          neighbour.setPrevious(current);
        }
      }
    }
  }
}
