//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.

public class TetrisGrid {

	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */

	private boolean[][] grid;

	public TetrisGrid(boolean[][] grid) {
		this.grid = grid;
	}


	private void sheamcireindeqsi(int y){
		for(int j = y; j < grid[0].length - 1; j ++){
			for(int i = 0; i < grid.length; i++){
				if(grid[i][j + 1]){
					grid[i][j] = true;
				} else {
					grid[i][j] = false;
				}
			}
		}
		for(int i = 0; i < grid.length; i++){
			grid[i][grid[0].length - 1] = false;
		}
	}

	/**
	 * Does row-clearing on the grid (see handout).
	 */
	public void clearRows() {
		for(int j = 0; j < grid[0].length; j ++){
			boolean tmp = true;
			for(int i = 0; i < grid.length; i ++){
				if(!(grid[i][j])){
					tmp = false;
					break;
				}
			}
			if(tmp) {
				sheamcireindeqsi(j);
				j--;
			}
		}
	}

	/**
	 * Returns the internal 2d grid array.
	 * @return 2d grid array
	 */
	boolean[][] getGrid() {
		//return null;
		return grid;
		// YOUR CODE HERE
	}

}
