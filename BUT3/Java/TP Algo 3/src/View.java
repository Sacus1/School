import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

public class View extends JPanel implements Observer {
  public Vertex[][] grid = new Vertex[Model.WIDTH][Model.HEIGHT];
  final JFrame frame;

  /** 0: Mur, 1: Départ, 2: Arrivée, 3: Vide */
  public final JButton[] caseSelector = new JButton[4];

  /** Start button */
  public final JButton startButton = new JButton("Start");

  /** Algorithm selector */
  public final JComboBox<String> algorithmSelector = new JComboBox<>();

  final JTextField waitTime;

  public View() {
    frame = new JFrame("Pathfinding");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(Model.WIDTH * (Model.SCALE + 1), Model.HEIGHT * (Model.SCALE + 8));
    frame.setResizable(false);
    frame.add(this);
    frame.setVisible(true);
    // set top bar
    JPanel topBar = new JPanel();
    topBar.setLayout(new GridLayout(1, 5));
    frame.add(topBar, BorderLayout.NORTH);
    // set wait time
    waitTime = new JTextField("1000");
    topBar.add(waitTime);
    // set buttons
    caseSelector[0] = new JButton("Mur");
    caseSelector[1] = new JButton("Départ");
    caseSelector[2] = new JButton("Arrivée");
    caseSelector[3] = new JButton("Vide");
    // add buttons to top bar
    for (JButton button : caseSelector) {
      topBar.add(button);
    }
    // add algorithm selector to top bar
    topBar.add(algorithmSelector);
    // add start button to top bar
    topBar.add(startButton);
  }

  @Override
  public void update(Observable o, Object arg) {
    if (o instanceof Model model) {
      grid = model.grid;
      repaint();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (int i = 0; i < Model.WIDTH; i++) {
      for (int j = 0; j < Model.HEIGHT; j++) {
        if (grid[i][j] == null) continue;
        switch (grid[i][j].type) {
          case WALL -> g.setColor(Color.BLACK);
          case EMPTY -> g.setColor(Color.WHITE);
          case DEPART -> g.setColor(Color.GREEN);
          case END -> g.setColor(Color.RED);
          case AVAILABLE -> g.setColor(Color.YELLOW);
          case PATH -> g.setColor(Color.BLUE);
          case EXPLORED -> g.setColor(Color.ORANGE);
          case CURRENT -> g.setColor(Color.PINK);
        }
        // draw square
        g.fillRect(i * Model.SCALE, j * Model.SCALE, Model.SCALE, Model.SCALE);
        // draw border
        g.setColor(Color.GRAY);
        g.drawRect(i * Model.SCALE, j * Model.SCALE, Model.SCALE, Model.SCALE);
      }
    }
    // draw arrow on top of each node
    g.setColor(Color.BLACK);
    for (int i = 0; i < Model.WIDTH; i++) {
      for (int j = 0; j < Model.HEIGHT; j++) {
        drawArrow(g, i, j);
      }
    }
  }

  private void drawArrow(Graphics g, int i, int j) {
    // draw arrow towards previous node
    if (grid[i][j] != null && grid[i][j].getPrevious() != null) {
      int arrowSize = 32;
      int x1 = i * Model.SCALE + Model.SCALE / 2;
      int y1 = j * Model.SCALE + Model.SCALE / 2;
      int x2 = grid[i][j].getPrevious().x * Model.SCALE + Model.SCALE / 2;
      int y2 = grid[i][j].getPrevious().y * Model.SCALE + Model.SCALE / 2;
      int direction = 0; // 0: right, 1: down, 2: left, 3: up
      if (y1 < y2) direction = 1;
      else if (x1 > x2) direction = 2;
      else if (y1 > y2) direction = 3;
      // draw arrow in order to stay in the same square as the current node
      switch (direction) {
        case 0 -> {
          x2 -= Model.SCALE / 2;
          g.drawLine(x2, y2, x2 - arrowSize, y2 - arrowSize / 2);
          g.drawLine(x2, y2, x2 - arrowSize, y2 + arrowSize / 2);
        }
        case 1 -> {
          y2 -= Model.SCALE / 2;
          g.drawLine(x2, y2, x2 - arrowSize / 2, y2 - arrowSize);
          g.drawLine(x2, y2, x2 + arrowSize / 2, y2 - arrowSize);
        }
        case 2 -> {
          x2 += Model.SCALE / 2;
          g.drawLine(x2, y2, x2 + arrowSize, y2 - arrowSize / 2);
          g.drawLine(x2, y2, x2 + arrowSize, y2 + arrowSize / 2);
        }
        case 3 -> {
          y2 += Model.SCALE / 2;

          g.drawLine(x2, y2, x2 - arrowSize / 2, y2 + arrowSize);
          g.drawLine(x2, y2, x2 + arrowSize / 2, y2 + arrowSize);
        }
      }
    }
  }
}
