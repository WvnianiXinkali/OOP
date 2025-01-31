import junit.framework.TestCase;


public class BoardTest extends TestCase {
	Board b;
	Piece pyr1, pyr2, pyr3, pyr4, s, sRotated;

	private Piece stick, rotatedStick;
	private Piece l1_Str, l1_rot1, l1_rot2, l1_rot3;

	private Piece box;

	private Piece[] pieces1;

	// This shows how to build things in setUp() to re-use
	// across tests.

	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.

	protected void setUp() throws Exception {
		b = new Board(3, 6);

		assertEquals(3, b.getWidth());
		assertEquals(6, b.getHeight());

		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();

		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();

		stick = new Piece(Piece.STICK_STR);
		rotatedStick = stick.computeNextRotation();

		l1_Str = new Piece(Piece.L1_STR);
		l1_rot1 = l1_Str.computeNextRotation();
		l1_rot2 = l1_rot1.computeNextRotation();
		l1_rot3 = l1_Str.computeNextRotation().computeNextRotation().computeNextRotation();

		pieces1 = Piece.getPieces();

		b.place(pyr1, 0, 0);
	}

	// Check the basic width/height/max after the one placement
	public void testSample1() {
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
	}

	// Place sRotated into the board, then check some measures
	public void testSample2() {
		b.commit();
		int result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(2, b.getRowWidth(1));
		assertEquals(2, b.getRowWidth(2));
		assertEquals(1, b.getRowWidth(3));
	}

	public void testSample3() {
		b.commit();
		int result = b.place(pyr2, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getRowWidth(1));
		assertEquals(2, b.getRowWidth(2));
		assertEquals(3, b.getColumnHeight(1));
		assertEquals(4, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(3));
		b.commit();
		result = b.place(pyr1, 0, 3);
		assertEquals(Board.PLACE_BAD, result);
		b.undo();
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getRowWidth(1));
		assertEquals(2, b.getRowWidth(2));
		assertEquals(3, b.getColumnHeight(1));
		assertEquals(4, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(3));

		result = b.place(pyr2, 0, 3);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(5, b.getColumnHeight(0));
		assertEquals(6, b.getColumnHeight(1));
		assertEquals(4, b.getColumnHeight(2));

		assertEquals(6, b.getMaxHeight());

		assertEquals(3, b.getRowWidth(0));
		assertEquals(2, b.getRowWidth(1));
		assertEquals(2, b.getRowWidth(2));
		assertEquals(2, b.getRowWidth(3));
		assertEquals(2, b.getRowWidth(4));
		assertEquals(1, b.getRowWidth(5));

	}

	public void testSample4(){
		b.undo();
		int res = b.place(pyr3, 0, 0);
		assertEquals(Board.PLACE_ROW_FILLED, res);
		assertEquals(2, b.dropHeight(sRotated, 0));
		b.commit();
		res = b.place(sRotated, 0, 2);
		assertEquals(Board.PLACE_OK, res);
		assertEquals(3, b.dropHeight(pyr2, 1));

		assertEquals(5, b.getColumnHeight(0));
		assertEquals(3, b.getRowWidth(1));
		assertEquals(1, b.getRowWidth(2));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(2, b.getColumnHeight(2));
		assertEquals(5, b.getMaxHeight());
		assertEquals(1, b.getRowWidth(0));
		assertEquals(2, b.getRowWidth(3));
		assertEquals(1, b.getRowWidth(4));

		b.commit();
		res = b.place(pyr2, 1, 3);
		assertEquals(Board.PLACE_ROW_FILLED, res);
		assertEquals(5, b.dropHeight(l1_rot3, 0));

		assertEquals(5, b.getColumnHeight(0));
		assertEquals(3, b.getRowWidth(1));
		assertEquals(1, b.getRowWidth(2));
		assertEquals(5, b.getColumnHeight(1));
		assertEquals(6, b.getColumnHeight(2));
		assertEquals(6, b.getMaxHeight());
		assertEquals(1, b.getRowWidth(0));
		assertEquals(3, b.getRowWidth(3));
		assertEquals(3, b.getRowWidth(4));
		assertEquals(1, b.getRowWidth(5));

		b.commit();

		res = b.place(l1_rot3, 0, 5);
		assertEquals(Board.PLACE_OUT_BOUNDS, res);

		b.undo();

		b.clearRows();

		assertEquals(2, b.dropHeight(pyr3, 0));

		assertEquals(0, b.getColumnHeight(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(1, b.getRowWidth(2));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(3, b.getMaxHeight());
		assertEquals(1, b.getRowWidth(0));
		assertEquals(0, b.getRowWidth(3));
		assertEquals(0, b.getRowWidth(4));
		assertEquals(0, b.getRowWidth(5));

	}
	public void testSample5(){
		b.undo();
		int res = b.place(pyr1, 0, 0);
		assertEquals(Board.PLACE_ROW_FILLED, res);

		b.clearRows();


		assertEquals(0, b.dropHeight(l1_rot3, 0));

		assertEquals(0, b.getColumnHeight(0));
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(0, b.getColumnHeight(2));
		assertEquals(1, b.getRowWidth(0));
		assertEquals(0, b.getRowWidth(1));

		b.undo();

		res = b.place(pyr1, 0, 0);

		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(1, b.getColumnHeight(2));
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));

		b.clearRows();

		b.commit();

		res = b.place(l1_rot3, 0, 0);
		assertEquals(Board.PLACE_ROW_FILLED, res);

		assertEquals(2, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getColumnHeight(2));
		assertEquals(2, b.getRowWidth(0));
		assertEquals(3, b.getRowWidth(1));

		b.clearRows();

		assertEquals(1, b.getColumnHeight(0));
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(0, b.getColumnHeight(2));
		assertEquals(2, b.getRowWidth(0));
		assertEquals(0, b.getRowWidth(1));

		assertTrue(b.getGrid(0,0));

		assertTrue(b.getGrid(0,10));

	}

}
