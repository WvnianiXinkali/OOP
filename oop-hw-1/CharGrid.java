// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

public class CharGrid {
	private char[][] grid;

	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public CharGrid(char[][] grid) {
		this.grid = grid;
	}

	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 */
	public int charArea(char ch) {
		//return 0;
		Point max = new Point(grid[0].length + 1, grid.length + 1);
		Point min = new Point(-1,-1);
		boolean isnt0 = false;
		for(int i = 0; i < grid.length; i ++){
			for(int j = 0; j < grid[0].length; j ++){
				if(grid[i][j] == ch){
					if(max.getX() > j){
						max = max.shiftedPoint(j - max.getX(), 0);
					}
					if(max.getY() > i){
						max = max.shiftedPoint(0, i - max.getY());
					}
					if(min.getX() < j){
						min = min.shiftedPoint(j - min.getX(), 0);
					}
					if(min.getY() < i){
						min = min.shiftedPoint(0, i - min.getY());
					}
					isnt0 = true;
				}
			}
		}
		double ans = 0;
		if(isnt0) ans = (min.getX() - max.getX() + 1) * (min.getY() - max.getY() + 1);
		return (int)ans;
		// YOUR CODE HERE
	}

	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */

	private int wingLength(int dX, int dY, int x, int y){
		int x1 = x + dX;
		int y1 = y + dY;
		int counter = 0;
		while(true) {
			if (y1 >= grid.length || x1 >= grid[0].length || y1 < 0 || x1 < 0 || grid[y][x] != grid[y1][x1]) {
				return counter;
			}
			counter++;
			x1 += dX;
			y1 += dY;
		}
	}

	public int countPlus() {
		//return 0;
		int crosses = 0;
		for(int i = 0; i < grid.length; i ++){
			for(int j = 0; j < grid[0].length; j++){
				int left = wingLength(-1, 0, j, i);
				int right = wingLength(1, 0, j, i);
				int up = wingLength(0, -1, j, i);
				int down = wingLength(0, 1, j, i);
				if(left == right && left == up && left == down && left >= 1){
					crosses++;
				}
			}
		}
		return crosses;
		// YOUR CODE HERE
	}
}
