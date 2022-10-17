import java.util.*;

public class DijkstraAlgorithm {
    public Set<Vertex> open;
    public Set<Vertex> closed;
    public int[] distance;
    public int[] previous;

    final Graph graph;

    public DijkstraAlgorithm(Graph graph) {
        this.graph = graph;
    }

    public void displayCurrentState() {
        System.out.println("Open: " + open);
        System.out.println("Closed: " + closed);
        System.out.println("Distance: " + Arrays.toString(distance));
        System.out.print("Previous : [");
        for (int j = 0; j < previous.length; j++) {
            if (previous[j] != -1)
                System.out.print(graph.getVertexes().get(j) + ":" + graph.getVertexes().get(previous[j]) + ",");
        }
        System.out.print("]");
    }

    /**
     * @param node The source node
     * @return List of neighbors
     */
    public List<Vertex> getNeighbors(Vertex node) {
        List<Vertex> neighbors = new ArrayList<>();
        for (Edge edge : graph.getEdges()) {
            if (edge.source().equals(node) && !closed.contains(edge.destination())) {
                neighbors.add(edge.destination());
            }
        }
        return neighbors;
    }

    /**
     * @param source The first node
     */
    public void init(Vertex source) {
        open = new HashSet<>();
        open.add(source);
        closed = new HashSet<>();
        distance = new int[graph.getVertexes().size()];
        previous = new int[graph.getVertexes().size()];
        for (int i = 0; i < distance.length; i++) {
            distance[i] = Integer.MAX_VALUE;
            previous[i] = -1;
        }
        distance[source.getId()] = 0;
    }

    /**
     * <br>Rq : les distances sont gardées dans la table "distance"
     *
     * @param nodes The nodes to look in
     * @return the node with the lowest distance
     */
    public Vertex getNodeWithLowestDistance(Set<Vertex> nodes) {
        Vertex lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Vertex vertex : nodes) {
            int vertexDistance = distance[vertex.getId()];
            if (vertexDistance < lowestDistance) {
                lowestDistance = vertexDistance;
                lowestDistanceNode = vertex;
            }
        }
        return lowestDistanceNode;
    }


    /**
     * @param target le sommet destination
     * @return le chemin depuis la source à la destination prend en paramètre
     */
    public LinkedList<Vertex> getPath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<>();
        Vertex step = target;
        // check if a path doesn't exist
        if (previous[step.getId()] == -1) {
            return null;
        }
        path.add(step);
        while (previous[step.getId()] != -1) {
            step = graph.getVertexes().get(previous[step.getId()]);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

    /**
     * l’algorithme de Dijkstra
     *
     * @param sourceNode Premier sommet
     */
    public void execute(Vertex sourceNode) throws EdgeNotFoundException {
        init(sourceNode);
        // tant qu’il y a des sommets à explorer
        while (!open.isEmpty()) {
            // on récupère le sommet avec la plus petite distance des sommets à explorer
            Vertex node = getNodeWithLowestDistance(Set.copyOf(open));
            //on supprime ce sommet (current) de l’ensemble OPEN
            open.remove(node);
            //on l’ajoute à l’ensemble CLOSED
            closed.add(node);
            //on récupère les voisins du sommet current (qui ne sont pas dans CLOSED).
            List<Vertex> neighbors = getNeighbors(node);
            // on les parcourt et on vérifie s’il faut mettre à jour les distances
            for (Vertex neighbor : neighbors) {
                // on récupère le coût de l’arête entre current et neighbor
                int edgeWeight = graph.getDistance(node, neighbor);
                // on additionne la distance jusqu’au current avec le coût de l’arête
                int newDistance = distance[node.getId()] + edgeWeight;
                //si la distance jusqu’à neighbor dans la table des distances est > à la nouvelle distance
                if (newDistance < distance[neighbor.getId()]) {
                    //on met à jour la distance dans la table des distances
                    distance[neighbor.getId()] = newDistance;
                    //on met à jour le prédécesseur dans la table des prédécesseurs
                    previous[neighbor.getId()] = node.getId();
                    //on ajoute le sommet dans l’ensemble OPEN
                    open.add(neighbor);
                }

            }
        }
    }
}
