public record Edge(int id, Vertex source, Vertex destination, int weight) {
    public Edge {
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Vertices cannot be null");
        }
    }
}
