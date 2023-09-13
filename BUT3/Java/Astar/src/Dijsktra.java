import java.util.ArrayList;

public class Dijsktra implements Algorithm {
		Model model;
		private ArrayList<Vertex> open;
		private ArrayList<Vertex> closed;

		public Dijsktra() {
			this.model = Model.getInstance();
		}

		public void findPath(Vertex source, Vertex destination) throws NoPathFoundException {
			open = new ArrayList<>();
			closed = new ArrayList<>();
			open.add(source);
			while (true) {
				Vertex current = getNodeWithLowestCost();
				if (current.equals(destination))
					return;

				open.remove(current);
				closed.add(current);
				for (Vertex neighbor : model.getAdjacent(current,closed)) {
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
				if (node.getGCost() < best.getGCost()) {
					best = node;
				}
			}
			drawVertex(best, model);
			return best;
		}
	}
