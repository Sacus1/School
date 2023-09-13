public class IDAStar implements Algorithm {
  Model model;

  public IDAStar() {
    this.model = Model.getInstance();
  }

  public void findPath(Vertex start, Vertex goal) throws NoPathFoundException {
    double threshold = start.setHCosts(goal); // set threshold to heuristic of start
    while (true) {
      double distance = search(start, goal, 0, threshold); // search for path
      if (distance == Double.MAX_VALUE) { // if no path found
        throw new NoPathFoundException();
      }
      if (distance < 0) { // if path found
        return;
      }
      threshold = distance; // set threshold to distance
    }
  }

  private double search(Vertex start, Vertex goal, double distance, double threshold) {
    if (start.equals(goal)) { // if goal found
      return -distance; // return negative distance
    }
    double estimate = distance + start.setHCosts(goal); // calculate estimate
    if (estimate > threshold) { // if estimate is greater than threshold
      return estimate; // return estimate
    }
    double min = Double.MAX_VALUE;
    for (Vertex neighbour : model.getAdjacent(start, null)) {
      double t = search(neighbour, goal, distance + start.calculateGCosts(neighbour), threshold);
      if (t < 0) {
        neighbour.setPrevious(start);
        return t;
      }
      if (t < min) {
        min = t;
      }
    }
    return min;
  }
}
