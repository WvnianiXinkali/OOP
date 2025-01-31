import junit.framework.TestCase;

import java.util.*;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest extends TestCase {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece pyr1, pyr2, pyr3, pyr4, pyr5;
	private Piece s, sRotated, s1;

	private Piece stick, rotatedStick, stick1;
	private Piece l1_Str, l1_rot1, l1_rot2, l1_rot3, l1_rot4;

	private Piece[] pieces1;

	protected void setUp() throws Exception {
		super.setUp();
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		pyr5 = pyr4.computeNextRotation();

		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();

		stick = new Piece(Piece.STICK_STR);
		rotatedStick = stick.computeNextRotation();
		stick1 = rotatedStick.computeNextRotation();

		l1_Str = new Piece(Piece.L1_STR);
		l1_rot1 = l1_Str.computeNextRotation();
		l1_rot2 = l1_rot1.computeNextRotation();
		l1_rot3 = l1_rot2.computeNextRotation();
		l1_rot4 = l1_rot3.computeNextRotation();

		pieces1 = Piece.getPieces();

	}
	
	// Here are some sample tests to get you started
	
	public void testSampleSize() {
		// Check size of pyr piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());
		
		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());

		assertEquals(3, pyr3.getWidth());
		assertEquals(2, pyr3.getHeight());

		assertEquals(2, pyr4.getWidth());
		assertEquals(3, pyr4.getHeight());
		
		// Now try with some other piece, made a different way
		Piece l = new Piece(Piece.STICK_STR);
		assertEquals(1, stick.getWidth());
		assertEquals(4, stick.getHeight());

		assertEquals(2, l1_Str.getWidth());
		assertEquals(3, l1_Str.getHeight());

		assertEquals(3, l1_rot1.getWidth());
		assertEquals(2, l1_rot1.getHeight());

		assertEquals(2, l1_rot2.getWidth());
		assertEquals(3, l1_rot2.getHeight());

		assertEquals(3, l1_rot3.getWidth());
		assertEquals(2, l1_rot3.getHeight());

		assertEquals(3, s.getWidth());
		assertEquals(2, s.getHeight());

		assertEquals(2, sRotated.getWidth());
		assertEquals(3, sRotated.getHeight());
	}
	
	
	// Test the skirt returned by a few pieces
	public void testSampleSkirt() {
		// Note must use assertTrue(Arrays.equals(... as plain .equals does not work
		// right for arrays.
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));

		assertTrue(Arrays.equals(new int[] {1, 0}, pyr2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 1}, pyr4.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, sRotated.getSkirt()));

		assertTrue(Arrays.equals(new int[] {0, 0}, l1_Str.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, l1_rot1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {2, 0}, l1_rot2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 1, 1}, l1_rot3.getSkirt()));

		assertTrue(Arrays.equals(new int[] {0}, stick.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 0, 0}, rotatedStick.getSkirt()));

	}

	public void testEquals(){
		assertTrue(pyr1.equals(pyr1));
		assertTrue(pyr1.equals(pyr5));
		assertTrue(stick.equals(stick1));
		assertTrue(l1_Str.equals(l1_rot4));
		assertTrue(pyr2.equals(pyr2));
		assertTrue(!pyr1.equals(pyr2));
		assertTrue(!pyr1.equals(pyr3));
		assertTrue(!pyr1.equals(pyr4));
		assertTrue(!pyr1.equals(sRotated));
		assertTrue(!s.equals(stick));
		String experimental = "0 0  1 0  1 1  2 0  3 0  4 0  1 2  1 3  1 4  0 1  0 2  0 3  0 4  2 1  2 2  2 3  2 4  3 1  3 2  3 3  3 4  4 1  4 2  4 3  4 4";
		Piece experimentaaal = new Piece(experimental);
		assertTrue(!pyr1.equals(experimentaaal));
		assertTrue(!pyr2.equals(experimentaaal));
		assertTrue(!pyr3.equals(experimentaaal));
		assertTrue(!pyr4.equals(experimentaaal));
		assertTrue(!s.equals(experimentaaal));
		assertTrue(!sRotated.equals(experimentaaal));
		assertTrue(!stick.equals(experimentaaal));
		assertTrue(!l1_rot1.equals(experimentaaal));
		assertTrue(!l1_Str.equals(experimentaaal));
		assertTrue(!experimentaaal.equals(pyr1));
		assertTrue(!experimentaaal.equals(pyr2));
		assertTrue(!experimentaaal.equals(pyr3));
		assertTrue(!experimentaaal.equals(pyr4));
		assertTrue(!experimentaaal.equals(s));
		assertTrue(!experimentaaal.equals(sRotated));
		assertTrue(!experimentaaal.equals(stick));
		assertTrue(!experimentaaal.equals(l1_rot1));
		assertTrue(!experimentaaal.equals(l1_Str));
	}

	public void testGetPiecesAndFastRot() {
		assertTrue(pyr1.equals(pieces1[Piece.PYRAMID]));
		assertTrue(l1_Str.equals(pieces1[Piece.L1]));
		assertTrue(stick.equals(pieces1[Piece.STICK]));
		assertTrue(s.equals(pieces1[Piece.S1]));

		assertTrue(pyr1.equals(pieces1[Piece.PYRAMID].fastRotation().fastRotation().fastRotation().fastRotation()));
		assertTrue(pieces1[Piece.PYRAMID].fastRotation().fastRotation().fastRotation().fastRotation().equals(pyr5));

		assertTrue(pyr2.equals(pieces1[Piece.PYRAMID].fastRotation()));
		assertTrue(pieces1[Piece.PYRAMID].fastRotation().equals(pyr2));

		assertTrue(pyr2.equals(pieces1[Piece.PYRAMID].fastRotation().fastRotation().fastRotation().fastRotation().fastRotation()));
		assertTrue(pieces1[Piece.PYRAMID].fastRotation().equals(pyr2));

		assertTrue(pyr3.equals(pieces1[Piece.PYRAMID].fastRotation().fastRotation()));
		assertTrue(pieces1[Piece.PYRAMID].fastRotation().fastRotation().equals(pyr3));

		assertTrue(l1_Str.equals(pieces1[Piece.L1].fastRotation().fastRotation().fastRotation().fastRotation()));
		assertTrue(stick.equals(pieces1[Piece.STICK].fastRotation().fastRotation()));
		assertTrue(rotatedStick.equals(pieces1[Piece.STICK].fastRotation()));

		assertTrue(s.equals(pieces1[Piece.S1].fastRotation().fastRotation()));

	}

	public void testBody(){
		assertTrue(pyr1.equals(pieces1[Piece.PYRAMID]));
		assertTrue(l1_Str.equals(pieces1[Piece.L1]));
		assertTrue(stick.equals(pieces1[Piece.STICK]));
		assertTrue(s.equals(pieces1[Piece.S1]));
	}
	
}
