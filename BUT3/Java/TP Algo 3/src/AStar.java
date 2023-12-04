import java.util.ArrayList;

public class AStar implements Algorithm {
  private ArrayList<Vertex> open;

  @Override
  public void findPath(Vertex source, Vertex destination) throws NoPathFoundException {
    ArrayList<Vertex> closed;
    open = new ArrayList<>();
    closed = new ArrayList<>();
    open.add(source);
    while (true) {
      Vertex current = getNodeWithLowestCost();
      if (current.equals(destination)) return;

      open.remove(current);
      closed.add(current);
      for (Vertex neighbor : model.getAdjacent(current, closed)) {
        if (open.contains(neighbor)) {
          // if the neighbor is already in the open list, check if the path to it is shorter
          // if yes, update the previous node, and the costs.
          if (neighbor.getGCost() > neighbor.calculateGCosts(current)) {
            neighbor.setGCosts(current);
            neighbor.setPrevious(current);
          }
        } else {
          open.add(neighbor);
          neighbor.setPrevious((current));
          neighbor.setGCosts(current);
          neighbor.setHCosts(destination);
        }
      }
      if (open.isEmpty()) {
        throw new NoPathFoundException();
      }
    }
  }

  private Vertex getNodeWithLowestCost() {
    Vertex best = open.get(0);
    for (Vertex node : open) {
      if (node.getFCost() < best.getFCost()) {
        best = node;
      }
    }
    drawVertex(best);
    return best;
  }
}
