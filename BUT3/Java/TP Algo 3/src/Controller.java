import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller {
	public final Model model;
	public final JButton wall;
	public final JButton depart;
	public final JButton end;
	public final JButton empty;
	public final JButton start;
	private int selectedType = 3;
	public final JComboBox<String> algorithmSelector;

	public Controller(Model model, View view) {
		this.model = model;
		wall = view.caseSelector[0];
		depart = view.caseSelector[1];
		end = view.caseSelector[2];
		empty = view.caseSelector[3];
		// add listeners for case selector
		wall.addActionListener(e -> selectedType = 0);
		depart.addActionListener(e -> selectedType = 1);
		end.addActionListener(e -> selectedType = 2);
		empty.addActionListener(e -> selectedType = 3);
		start = view.startButton;
		start.addActionListener(e -> model.findPath());
		algorithmSelector = view.algorithmSelector;
		String[] algorithms = {"Breadth first", "Depth first", "Greedy best first", "Dijkstra", "A*", "IDA*"};
		for (String algorithm : algorithms)
			algorithmSelector.addItem(algorithm);

		// add listeners for algorithm selector
		algorithmSelector.addActionListener(e -> algorithmSelector());
		// listen for mouse clicks
		view.addMouseListener(new MouseListener());
		algorithmSelector.setSelectedIndex(0);
		// detect if user changes wait time
		view.waitTime.addActionListener(e -> {
			try {
				model.waitTime = Integer.parseInt(view.waitTime.getText());
				System.out.println("Wait time set to " + model.waitTime);
			} catch (NumberFormatException ignored) {
				System.err.println("Invalid wait time");
			}
		});
	}

	private void algorithmSelector() {
		if (model.isRunning) {
			System.err.println("Already running");
			return;
		}
		Algorithm[] algorithms = {new BreadthFirst(), new DepthFirst(), new GreedyBestFirst(), new Dijkstra(), new AStar()
						, new IDAStar()};
		model.algorithm = algorithms[algorithmSelector.getSelectedIndex()];
	}
	private class MouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			mouseListener(e);
		}

		private void mouseListener(MouseEvent e) {
			super.mousePressed(e);
			if (model.isRunning) return;
			int x = e.getX() / Model.SCALE;
			int y = e.getY() / Model.SCALE;
			if (x >= 0 && x < Model.WIDTH && y >= 0 && y < Model.HEIGHT) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					// left click
					switch (selectedType) {
						case 0 -> selectWall(x, y);
						case 1 -> selectStart(x, y);
						case 2 -> selectEnd(x, y);
						case 3 -> selectEmpty(x, y);
						default -> throw new IllegalStateException("Unexpected value: " + selectedType);
					}
				}
				model.update();
			}
		}

		private void selectWall(int x, int y) {
			if (model.grid[x][y].type == Type.DEPART) model.start = null;
			if (model.grid[x][y].type == Type.END) model.end = null;
			model.grid[x][y].type = Type.WALL;
		}

		private void selectStart(int x, int y) {
			if (model.start != null) model.start.type = Type.EMPTY;
			model.start = model.grid[x][y];
			model.start.type = Type.DEPART;
		}

		private void selectEnd(int x, int y) {
			if (model.end != null) model.end.type = Type.EMPTY;
			model.end = model.grid[x][y];
			model.end.type = Type.END;
		}

		private void selectEmpty(int x, int y) {
			if (model.grid[x][y].type == Type.DEPART) model.start = null;
			if (model.grid[x][y].type == Type.END) model.end = null;
			model.grid[x][y].type = Type.EMPTY;
		}
	}
}
