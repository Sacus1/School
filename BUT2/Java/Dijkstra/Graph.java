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
     * @throws Exception When there is no edge between node and target
     */
    public int getDistance(Vertex node, Vertex target) throws Exception {
        for (Edge edge : edges) {
            if (edge.getSource().equals(node) && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new Exception("No edge between " + node + " and " + target);
    }

    /**
     * print each node , its neighbours and the distance between them
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Vertex vertex : vertexes) {
            sb.append("Node ").append(vertex).append(" is connected to:\n");
            for (Edge edge : edges) {
                if (edge.getSource().equals(vertex)) {
                    sb.append("Node ").append(edge.getDestination()).append(" with distance ").append(edge.getWeight()).append("\n");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
