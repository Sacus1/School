import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Model extends Observable {
	public Algorithm algorithm;
	public Vertex start;
	public Vertex end;
	public static final int WIDTH = 16;
	public static final int HEIGHT = 9;
	/**
	 * Size of a square in pixels
	 */
	public static final int SCALE = 64;
	public final Vertex[][] grid = new Vertex[WIDTH][HEIGHT];

	public static  Model instance;

	public boolean isRunning = false;
	int waitTime = 1000;

	static Model getInstance() {
		if (instance == null) {
			instance = new Model();
		}
		return instance;
	}

	Model () {
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) grid[i][j] = new Vertex(i, j);
		}
	}

	public void update() {
		setChanged();
		notifyObservers();
	}
	/**
	 * @param current The source node
	 * @return List of neighbors
	 */
	List<Vertex> getAdjacent(Vertex current, ArrayList<Vertex> closed) {
		List<Vertex> neighbors = new ArrayList<>();
		int x = current.x;
		int y = current.y;
		addAdjacent(x+1,y,closed, neighbors);
		addAdjacent(x-1,y,closed, neighbors);
		addAdjacent(x,y+1,closed, neighbors);
		addAdjacent(x,y-1,closed, neighbors);
		return neighbors;
	}

	private void addAdjacent(int i, int j, ArrayList<Vertex> closed, List<Vertex> neighbors) {
		if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length && grid[i][j].isWalkable() && (closed == null || !closed.contains(grid[i][j]))) {
			neighbors.add(grid[i][j]);
		if (grid[i][j].type != Type.END && grid[i][j].type != Type.DEPART && grid[i][j].type != Type.WALL && grid[i][j].type != Type.EXPLORED) {
			grid[i][j].type = Type.AVAILABLE;
		}
		update();
		}
	}

	public void findPath(){
		// check if isRunning
		if (isRunning) {
			Logger.error("Already running");
			return;
		}
		// check if algorithm is set
		if (algorithm == null) {
			Logger.error("Algorithm not set");
			return;
		}
		// check if start and end are set
		if (start == null || end == null) {
			Logger.error("Start or end not set");
			return;
		}
		// remove path from grid
		for (int j = 0; j < Model.HEIGHT; j++) {
			for (int i = 0; i < Model.WIDTH; i++) {
				if (grid[i][j].type == Type.AVAILABLE || grid[i][j].type == Type.PATH || grid[i][j].type == Type.EXPLORED)
				{
					grid[i][j].type = Type.EMPTY;
					grid[i][j].setPrevious(null);
				}
			}
		}
		isRunning = true;
		new Thread(this::run).start();
	}

	private void run() {
		try {
			algorithm.findPath(start, end);
			// set path to grid
			Vertex current = end;
			while (current != start) {
				if (current.type != Type.END && current.type != Type.DEPART)
					current.type = Type.PATH;
				current = current.getPrevious();
			}
			update();
			isRunning = false;
		} catch (NoPathFoundException ex) {
			Logger.error("Path not found");
		}

	}
}
