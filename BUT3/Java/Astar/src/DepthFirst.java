import java.util.ArrayList;

public class DepthFirst implements Algorithm {

  Model model;
  boolean finished;
  ArrayList<Vertex> visited;

  public DepthFirst() {
    this.model = Model.getInstance();
  }

  public void findPath(Vertex source, Vertex destination) throws NoPathFoundException {
    visited = new ArrayList<>();
    finished = false;
    DFS(source, destination);
    if (!finished) throw new NoPathFoundException();
  }

  public void DFS(Vertex source, Vertex destination) {
    visited.add(source);
    if (source == destination) {
      finished = true;
    }
    if (finished) return;
    for (Vertex neighbour : model.getAdjacent(source, visited)) {
      neighbour.setPrevious(source);
      DFS(neighbour, destination);
    }
  }
}
