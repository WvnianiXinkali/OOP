
// Test cases for CharGrid -- a few basic tests are provided.

import junit.framework.TestCase;

public class CharGridTest extends TestCase {

	public void testCharArea1() {
		char[][] grid = new char[][] {
				{'a', 'y', ' '},
				{'x', 'a', 'z'},
		};


		CharGrid cg = new CharGrid(grid);

		assertEquals(4, cg.charArea('a'));
		assertEquals(1, cg.charArea('z'));
	}


	public void testCharArea2() {
		char[][] grid = new char[][] {
				{'c', 'a', ' '},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'}
		};

		CharGrid cg = new CharGrid(grid);

		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(1, cg.charArea('c'));
	}

	public  void testGrid(){
		char[][] grid = new char[][] {
				{'c', 'b', ' '},
				{'b', 'b', 'b'},
				{' ', 'b', 'a'},
		};

		CharGrid k = new CharGrid(grid);

		assertEquals(1, k.countPlus());
	}

	public  void testGrid1(){
		char[][] grid = new char[][] {
				{'c', 'b', ' ', ' ', ' ', ' ','b','c','a','k'},
				{'b', 'b', 'b', ' ', ' ', ' ','a','a','a','a'},
				{' ', 'b', 'a', ' ', ' ', ' ','x','a','d','d'}
		};

		CharGrid k = new CharGrid(grid);

		assertEquals(2, k.countPlus());
	}
}
