import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
	// Provided grid data for main/testing
	// The instance variable strategy is up to you.
	
	// Provided easy 1 6 grid
	// (can paste this text into the GUI too)
	public static final int[][] easyGrid = Sudoku.stringsToGrid(
	"1 6 4 0 0 0 0 0 2",
	"2 0 0 4 0 3 9 1 0",
	"0 0 5 0 8 0 4 0 7",
	"0 9 0 0 0 6 5 0 0",
	"5 0 0 1 0 2 0 0 8",
	"0 0 8 9 0 0 0 3 0",
	"8 0 9 0 4 0 2 0 0",
	"0 7 3 5 0 9 0 0 1",
	"4 0 0 0 0 0 6 7 9");
	
	
	// Provided medium 5 3 grid
	public static final int[][] mediumGrid = Sudoku.stringsToGrid(
	 "530070000",
	 "600195000",
	 "098000060",
	 "800060003",
	 "400803001",
	 "700020006",
	 "060000280",
	 "000419005",
	 "000080079");
	
	// Provided hard 3 7 grid
	// 1 solution this way, 6 solutions if the 7 is changed to 0
	public static final int[][] hardGrid = Sudoku.stringsToGrid(
	"3 7 0 0 0 0 0 8 0",
	"0 0 1 0 9 3 0 0 0",
	"0 4 0 7 8 0 0 0 3",
	"0 9 3 8 0 0 0 1 2",
	"0 0 0 0 4 0 0 0 0",
	"5 2 0 0 0 6 7 9 0",
	"6 0 0 0 2 1 0 4 0",
	"0 0 0 5 3 0 9 0 0",
	"0 3 0 0 0 0 0 5 1");
	public static final int[][] hardGrid1 = Sudoku.stringsToGrid(
			"0 0 0 0 0 0 0 0 0",
			"0 0 0 0 0 3 0 0 0",
			"0 4 0 7 8 0 0 0 3",
			"0 9 3 8 0 0 0 1 2",
			"0 0 0 0 4 0 0 0 0",
			"5 0 0 0 0 6 7 9 0",
			"0 0 0 0 2 1 0 4 0",
			"0 0 0 0 3 0 9 0 0",
			"0 3 0 0 0 0 0 0 1");
	
	public static final int SIZE = 9;  // size of the whole 9x9 puzzle
	public static final int PART = 3;  // size of each 3x3 part
	public static final int MAX_SOLUTIONS = 100;
	
	// Provided various static utility methods to
	// convert data formats to int[][] grid.
	
	/**
	 * Returns a 2-d grid parsed from strings, one string per row.
	 * The "..." is a Java 5 feature that essentially
	 * makes "rows" a String[] array.
	 * (provided utility)
	 * @param rows array of row strings
	 * @return grid
	 */
	public static int[][] stringsToGrid(String... rows) {
		int[][] result = new int[rows.length][];
		for (int row = 0; row<rows.length; row++) {
			result[row] = stringToInts(rows[row]);
		}
		return result;
	}
	
	
	/**
	 * Given a single string containing 81 numbers, returns a 9x9 grid.
	 * Skips all the non-numbers in the text.
	 * (provided utility)
	 * @param text string of 81 numbers
	 * @return grid
	 */
	public static int[][] textToGrid(String text) {
		int[] nums = stringToInts(text);
		if (nums.length != SIZE*SIZE) {
			throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
		}
		
		int[][] result = new int[SIZE][SIZE];
		int count = 0;
		for (int row = 0; row<SIZE; row++) {
			for (int col=0; col<SIZE; col++) {
				result[row][col] = nums[count];
				count++;
			}
		}
		return result;
	}
	
	
	/**
	 * Given a string containing digits, like "1 23 4",
	 * returns an int[] of those digits {1 2 3 4}.
	 * (provided utility)
	 * @param string string containing ints
	 * @return array of ints
	 */
	public static int[] stringToInts(String string) {
		int[] a = new int[string.length()];
		int found = 0;
		for (int i=0; i<string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				a[found] = Integer.parseInt(string.substring(i, i+1));
				found++;
			}
		}
		int[] result = new int[found];
		System.arraycopy(a, 0, result, 0, found);
		return result;
	}


	// Provided -- the deliverable main().
	// You can edit to do easier cases, but turn in
	// solving hardGrid.
	public static void main(String[] args) {
		Sudoku sudoku;
		sudoku = new Sudoku(hardGrid);
		
		System.out.println(sudoku); // print the raw problem
		int count = sudoku.solve();
		System.out.println("solutions:" + count);
		System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
		System.out.println(sudoku.getSolutionText());
	}
	
	private int[][] grid;
	private int[][] gridimmutable;

	private int[][] firstSol;
	long timeNeeded;

	public class spot implements Comparable<spot>{
		private int x;
		private int y;

		public spot(int y, int x){
			this.x = x;
			this.y = y;
		}

		public void set(int val){
			grid[y][x] = val;
		}

		public void clear(){
			grid[y][x] = 0;
		}

		public HashSet<Integer> possibleMoves(){
			HashSet<Integer> tmp = new HashSet<Integer>();

			for(int i = 0; i < grid.length; i++){
				if(grid[i][x] != 0) tmp.add(grid[i][x]);
			}

			for(int i = 0; i < grid.length; i++){
				if(grid[y][i] != 0) tmp.add(grid[y][i]);
			}

			int tmpx = (x / 3) * 3;
			int tmpy = (y / 3) * 3;
			for(int i = tmpy; i < tmpy + 3; i++){
				for(int j = tmpx; j < tmpx + 3; j++){
					if(grid[i][j] != 0) tmp.add(grid[i][j]);
				}
			}
			HashSet<Integer> assignableValues = new HashSet<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
			assignableValues.removeAll(tmp);
			return assignableValues;
		}
		public int compareTo(spot anotherspot) {
			return this.possibleMoves().size() - anotherspot.possibleMoves().size();
		}
	}

	private String printBoard(int[][] gird1){
		String ans = "";
		for(int i = 0; i < gird1.length; i++){
			String row = "";
			for(int j = 0; j < gird1[0].length; j++){
				row += gird1[i][j];
				if(j != gird1[0].length - 1) row += " ";
			}
			ans += row;
			if(i != gird1.length - 1) ans += "\n";
		}
		return ans;
	}

	/**
	 * Sets up based on the given ints.
	 */
	private void copyGrids(int[][] grid1, int[][] grid2){
		for(int i = 0; i < grid1.length; i++){
			for(int j = 0; j < grid1[0].length; j++){
				grid2[i][j] = grid1[i][j];
			}
		}
	}
	public Sudoku(int[][] ints) {
		// YOUR CODE HERE
		grid = new int[ints.length][ints[0].length];
		gridimmutable = new int[ints.length][ints[0].length];
		firstSol = new int[ints.length][ints[0].length];
		copyGrids(ints, grid);
		copyGrids(ints, gridimmutable);
		timeNeeded = 0;
	}

	public Sudoku(String text) {
		// YOUR CODE HERE
		grid = textToGrid(text);
		gridimmutable = textToGrid(text);
		firstSol = new int[grid.length][grid[0].length];
		timeNeeded = 0;
	}
	@Override
	public String toString(){
		return printBoard(gridimmutable);
	}
	private int rec(int solcnt, ArrayList<spot> points) {
		if(solcnt >= 100) return solcnt;
		if (points.isEmpty()) {
			copyGrids(grid, firstSol);
			return solcnt + 1;
		}
		spot point = points.get(0);
		HashSet<Integer> curr = point.possibleMoves();
		if (curr.isEmpty()) return solcnt;

		points.remove(0);
		for (int value : curr) {
			point.set(value);
			solcnt = rec(solcnt, points);
			point.clear();
			if(solcnt >= 100) break;
		}
		points.add(0, point);
		return solcnt;
	}
	/**
	 * Solves the puzzle, invoking the underlying recursive search.
	 */
	public int solve() {
		ArrayList<spot> points = new ArrayList<>();
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[0].length; j++){
				if(grid[i][j] == 0){
					points.add(new spot(i, j));
				}
			}
		}
		Collections.sort(points);
		long started = System.currentTimeMillis();
		int solcnt = rec(0, points);
		long ended = System.currentTimeMillis();
		timeNeeded = ended - started;
		return solcnt; // YOUR CODE HERE
	}
	
	public String getSolutionText() {
		return printBoard(firstSol);
	}
	
	public long getElapsed() {
		return timeNeeded; // YOUR CODE HERE
	}

}
