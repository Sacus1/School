import java.util.ArrayList;
import java.util.List;

public class Graph {
  private final List<Vertex> vertexes;
  private final List<Edge> edges;

  public Graph() {
    this.vertexes = new ArrayList<>();
    this.edges = new ArrayList<>();
  }

  public List<Vertex> getVertexes() {
    return vertexes;
  }

  public List<Edge> getEdges() {
    return edges;
  }

  public void addVertex(Vertex vertex) {
    this.vertexes.add(vertex);
  }

  public void addEdge(Edge edge) {
    this.edges.add(edge);
  }

  /**
   * @param node The source node
   * @param target Target node
   * @return the edge between node and target
   * @throws EdgeNotFoundException When there is no edge between node and target
   */
  public int getDistance(Vertex node, Vertex target) throws EdgeNotFoundException {
    for (Edge edge : edges) {
      if (edge.source().equals(node) && edge.destination().equals(target)) {
        return edge.weight();
      }
    }
    throw new EdgeNotFoundException(node, target);
  }

  /** print each node , its neighbours and the distance between them */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Vertex vertex : vertexes) {
      sb.append("Node ").append(vertex).append(" is connected to:\n");
      for (Edge edge : edges) {
        if (edge.source().equals(vertex)) {
          sb.append("Node ")
              .append(edge.destination())
              .append(" with distance ")
              .append(edge.weight())
              .append("\n");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
