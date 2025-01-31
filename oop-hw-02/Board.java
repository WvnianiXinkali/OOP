// Board.java

import java.util.HashMap;

import static java.lang.Math.max;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private boolean[][] grid;
	private int[] widths;
	private int[] heights;
	private int maxHeight;
	private boolean DEBUG = true;
	boolean committed;

	private boolean[][] xGrid;
	private int[] xWidths;
	private int[] xHeights;

	private int xMaxHeight;
	
	// Here a few trivial methods are provided:
	
	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new boolean[width][height];
		committed = true;

		// YOUR CODE HERE

		xGrid = new boolean[width][height];
		widths = new int[height];
		heights = new int[width];
		xWidths = new int[height];
		xHeights = new int[width];
		maxHeight = 0;
		xMaxHeight = 0;
	}
	
	
	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}
	
	
	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}
	
	
	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {return maxHeight; // YOUR CODE HERE
	}
	
	
	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() {
		if (DEBUG) {
			// YOUR CODE HERE
			int tmpMaxHeight = 0;
			int[] tmpHeight = new int[width];
			for(int i = 0; i < width; i ++){
				tmpHeight[i] = 0;
			}
			int tmpWidth = 0;

			for(int j = 0; j < height; j ++) {
				for (int i = 0; i < width; i++) {
					if(grid[i][j]) {
						tmpWidth ++;
						tmpMaxHeight = max(tmpMaxHeight, j + 1);
						tmpHeight[i] = j + 1;
					}
				}
				if(widths[j] != tmpWidth) throw new RuntimeException("Widths's " + j + "'th row wrong val: Actual- " + tmpWidth + " yours-" + widths[j]);
				tmpWidth = 0;
			}
			if(tmpMaxHeight != maxHeight) throw new RuntimeException("maxHeight is wrong: Actual- " + tmpMaxHeight + " yours: " + maxHeight);
			for(int i = 0; i < width; i++){
				if(tmpHeight[i] != heights[i]) throw new RuntimeException("Heights's " + i + "'th col wrong val: Actual- " + tmpHeight[i] + " yours-" +  heights[i]);
			}
		}
	}
	
	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.
	 
	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
		int ans = 0;
		int[] skirt = piece.getSkirt();
		for(int i = 0; i < piece.getWidth(); i++){
			ans = max(ans, heights[x + i] - skirt[i]);
		}
		return ans; // YOUR CODE HERE
	}
	
	
	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
		return heights[x]; // YOUR CODE HERE
	}
	
	
	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		 return widths[y]; // YOUR CODE HERE
	}

	private boolean outOfBounds(int x, int y){
		if(x < 0 || y < 0 || x >= width || y >= height) return true;
		return false;
	}
	
	
	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		if(outOfBounds(x,y)) return true;
		return grid[x][y]; // YOUR CODE HERE
	}

	private void makeCheckPoint(){
		xMaxHeight = maxHeight;
		System.arraycopy(widths, 0, xWidths, 0, widths.length);
		System.arraycopy(heights, 0, xHeights, 0, heights.length);
		for(int i = 0;i < width; i++)
			System.arraycopy(grid[i], 0, xGrid[i], 0, grid[i].length);
	}
	
	
	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;
	
	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.
	 
	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");
			
		int result = PLACE_OK;

		// YOUR CODE HERE
		committed = false;
		makeCheckPoint();

		TPoint[] body1 = piece.getBody();
		for(int i = 0; i < body1.length; i++){
			int x1 = x + body1[i].x;
			int y1 = y + body1[i].y;

			if(outOfBounds(x1, y1)) return PLACE_OUT_BOUNDS;

			if(grid[x1][y1]) return PLACE_BAD;

			grid[x1][y1] = true;
			widths[y1]++;
			if(widths[y1] == width) result = PLACE_ROW_FILLED;
			heights[x1] = max(y1 + 1, heights[x1]);
			maxHeight = max(maxHeight, y1 + 1);
		}

		sanityCheck();
		return result;
	}


	
	
	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {
		int rowsCleared = 0;
		// YOUR CODE HERE
		if(committed){
			committed = false;
			makeCheckPoint();
		}

		maxHeight = 0;
		for(int i = 0; i < width; i ++){
			heights[i] = 0;
		}
		for(int j = 0; j < height; j ++){
			if(widths[j] == width) {
				rowsCleared++;
				widths[j] = 0;
				for (int k = 0; k < width; k++) grid[k][j] = false;
			} else {
				for (int k = 0; k < width; k++) {
					grid[k][j - rowsCleared] = grid[k][j];
					if(rowsCleared >= 1) grid[k][j] = false;
					if(grid[k][j - rowsCleared]) {
						heights[k] = j - rowsCleared + 1;
						maxHeight = max(maxHeight, j - rowsCleared + 1);
					}
				}
				widths[j - rowsCleared] = widths[j];
				if(rowsCleared >= 1) widths[j] = 0;
			}
		}
		sanityCheck();
		return rowsCleared;
	}




	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
		// YOUR CODE HERE
		if(committed) return;

		boolean[][] tmp = xGrid;
		xGrid = grid;
		grid = tmp;

		int[] temp = xWidths;
		xWidths = widths;
		widths = temp;

		int[] temp1 = xHeights;
		xHeights = heights;
		heights = temp1;

		int temp2 = xMaxHeight;
		xMaxHeight = maxHeight;
		maxHeight = temp2;

		commit();
		sanityCheck();
	}
	
	
	/**
	 Puts the board in the committed state.
	*/
	public void commit() {
		committed = true;
	}


	
	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility) 
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}


