import java.util.Objects;

public class Vertex {
  public final int x;
  public final int y;
  private Vertex previous;
  private int gCost;
  private int hCost = -1;
  public Type type = Type.EMPTY;

  public Vertex(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getFCost() {
    return gCost + hCost;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Vertex vertex)) return false;
    return vertex.x == x && vertex.y == y;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  int calculateGCosts(Vertex current) {
    return (current.gCost + 10);
  }

  void setGCosts(Vertex current) {
    gCost = calculateGCosts(current);
  }

  int setHCosts(Vertex destination) {
    return hCost = (Math.abs(destination.x - x) + Math.abs(destination.y - y)) * 15;
  }

  @Override
  public String toString() {
    return x + " " + y;
  }

  public boolean isWalkable() {
    return type != Type.WALL;
  }

  public int getGCost() {
    return gCost;
  }

  public Vertex getPrevious() {
    return previous;
  }

  public void setPrevious(Vertex previous) {
    this.previous = previous;
  }
}
