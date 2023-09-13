import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller  {
	public Model model;
	public JButton wall, depart, end, empty, start;
	private int selectedType = 3;
	public JComboBox<String> algorithmSelector;
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
		start.addActionListener(e -> {
			model.findPath();
		});
		algorithmSelector = view.algorithmSelector;
		algorithmSelector.addItem("Breadth first");
		algorithmSelector.addItem("Depth first");
		algorithmSelector.addItem("Greedy best first");
		algorithmSelector.addItem("Dijkstra");
		algorithmSelector.addItem("A*");
		algorithmSelector.addItem("IDA*");
		// add listeners for algorithm selector
		algorithmSelector.addActionListener(e -> algorithmSelector());
		// listen for mouse clicks
		view.addMouseListener(new MouseAdapter() {
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
							case 0 -> {
								if (model.grid[x][y].type == Type.DEPART) model.start = null;
								if (model.grid[x][y].type == Type.END) model.end = null;
								model.grid[x][y].type = Type.WALL;
							}
							case 1 -> {
								if (model.start != null) model.start.type = Type.EMPTY;
								model.start = model.grid[x][y];
								model.start.type = Type.DEPART;
							}
							case 2 -> {
								if (model.end != null) model.end.type = Type.EMPTY;
								model.end = model.grid[x][y];
								model.end.type = Type.END;
							}
							case 3 -> {
								if (model.grid[x][y].type == Type.DEPART) model.start = null;
								if (model.grid[x][y].type == Type.END) model.end = null;
								model.grid[x][y].type = Type.EMPTY;
							}
							default -> throw new IllegalStateException("Unexpected value: " + selectedType);
						}
					}
					model.update();
				}
			}
		});
		algorithmSelector.setSelectedIndex(0);
		// detect if user changes wait time
		view.waitTime.addActionListener(e -> {
			try {
				model.waitTime = Integer.parseInt(view.waitTime.getText());
				System.out.println("Wait time set to " + model.waitTime);
			} catch (NumberFormatException ignored) {
			}
		});
	}

	private void algorithmSelector() {
		if (model.isRunning) {
			System.err.println("Already running");
			return;
		}
		switch (algorithmSelector.getSelectedIndex()) {
			case 0 -> model.algorithm = new BreadthFirst();
			case 1 -> model.algorithm = new DepthFirst();
			case 2 -> model.algorithm = new GreedyBestFirst();
			case 3 -> model.algorithm = new Dijsktra();
			case 4 -> model.algorithm = new AStar();
			case 5 -> model.algorithm = new IDAStar();
			default -> throw new IllegalStateException("Unexpected value: " + algorithmSelector.getSelectedIndex());
		}
	}
}
