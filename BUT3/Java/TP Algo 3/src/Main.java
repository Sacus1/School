public class Main {
  public static void main(String[] args) {
    Model model = Model.getInstance();
    View view = new View();
    new Controller(model, view);

    model.addObserver(view);
    model.update();
  }
}
