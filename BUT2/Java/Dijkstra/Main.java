public class Main {
  public static Graph graph1() {
    Graph graph = new Graph();
    Vertex v1 = new Vertex(0, "v1");
    Vertex v2 = new Vertex(1, "v2");
    Vertex v3 = new Vertex(2, "v3");
    Vertex v4 = new Vertex(3, "v4");
    Vertex v5 = new Vertex(4, "v5");
    Vertex v6 = new Vertex(5, "v6");
    graph.addVertex(v1);
    graph.addVertex(v2);
    graph.addVertex(v3);
    graph.addVertex(v4);
    graph.addVertex(v5);
    graph.addVertex(v6);
    graph.addEdge(new Edge(0, v1, v2, 3));
    graph.addEdge(new Edge(1, v1, v3, 4));
    graph.addEdge(new Edge(2, v1, v4, 2));
    graph.addEdge(new Edge(3, v2, v3, 4));
    graph.addEdge(new Edge(4, v2, v5, 2));
    graph.addEdge(new Edge(5, v3, v5, 6));
    graph.addEdge(new Edge(6, v4, v5, 1));
    graph.addEdge(new Edge(7, v4, v6, 4));
    graph.addEdge(new Edge(8, v5, v6, 2));
    // connect on the other side
    graph.addEdge(new Edge(9, v2, v1, 3));
    graph.addEdge(new Edge(10, v3, v1, 4));
    graph.addEdge(new Edge(11, v4, v1, 2));
    graph.addEdge(new Edge(12, v3, v2, 4));
    graph.addEdge(new Edge(13, v5, v2, 2));
    graph.addEdge(new Edge(14, v5, v3, 6));
    graph.addEdge(new Edge(15, v5, v4, 1));
    graph.addEdge(new Edge(16, v6, v4, 4));
    graph.addEdge(new Edge(17, v6, v5, 2));
    return graph;
  }

  public static Graph graph2() {
    Graph graph = new Graph();
    Vertex A = new Vertex(0, "A");
    Vertex B = new Vertex(1, "B");
    Vertex C = new Vertex(2, "C");
    Vertex D = new Vertex(3, "D");
    Vertex E = new Vertex(4, "E");
    Vertex F = new Vertex(5, "F");
    graph.addVertex(A);
    graph.addVertex(B);
    graph.addVertex(C);
    graph.addVertex(D);
    graph.addVertex(E);
    graph.addVertex(F);
    graph.addEdge(new Edge(0, A, C, 3));
    graph.addEdge(new Edge(1, A, F, 1));
    graph.addEdge(new Edge(2, C, B, 2));
    graph.addEdge(new Edge(3, C, E, 3));
    graph.addEdge(new Edge(4, C, F, 1));
    graph.addEdge(new Edge(4, F, E, 5));
    graph.addEdge(new Edge(5, B, D, 3));
    graph.addEdge(new Edge(6, B, E, 1));
    graph.addEdge(new Edge(7, E, D, 1));
    // connect on the other side
    graph.addEdge(new Edge(8, C, A, 3));
    graph.addEdge(new Edge(9, F, A, 1));
    graph.addEdge(new Edge(10, B, C, 2));
    graph.addEdge(new Edge(11, E, C, 3));
    graph.addEdge(new Edge(12, F, C, 1));
    graph.addEdge(new Edge(13, E, F, 5));
    graph.addEdge(new Edge(14, D, B, 3));
    graph.addEdge(new Edge(15, E, B, 1));
    graph.addEdge(new Edge(16, D, E, 1));

    return graph;
  }

  public static void main(String[] args) {

    Graph graph = graph1();
    System.out.println(graph);
    DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
    try {
      dijkstra.execute(graph.getVertexes().get(0));
    } catch (EdgeNotFoundException e) {
      e.printStackTrace();
    }
    dijkstra.displayCurrentState();
    System.out.println(
        "\nShortest path from V1 to V6: " + dijkstra.getPath(graph.getVertexes().get(3)));
    graph = graph2();
    dijkstra = new DijkstraAlgorithm(graph);
    try {
      dijkstra.execute(graph.getVertexes().get(0));
    } catch (EdgeNotFoundException e) {
      e.printStackTrace();
    }
    System.out.println(
        "Shortest path from A to E: " + dijkstra.getPath(graph.getVertexes().get(3)));
  }
}
