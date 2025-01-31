import junit.framework.TestCase;
import java.util.Vector;

public class SudokuTest extends TestCase {
    private Sudoku sudokuUnsolvable, sudoku1Sol, sudokuManySol, sudokuEasy, sudokuMedium, sudokuHard,sudokuHardModification;

    private static final int[][] sudokuUnsolvable1 = Sudoku.stringsToGrid(
            "1 2 3 0 0 0 0 0 0",
            "0 0 0 0 4 0 0 0 0",
            "0 0 0 0 0 0 0 0 4",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0");
    private static final int[][] sudoku1Sol1 = Sudoku.stringsToGrid(
            "0 0 0 0 7 0 9 1 3",
            "0 0 2 9 0 0 6 0 0",
            "1 0 0 6 0 0 0 0 0",
            "2 0 0 7 0 0 5 3 0",
            "0 3 4 5 0 0 0 0 2",
            "0 8 0 0 2 0 0 7 9",
            "0 2 6 0 0 0 0 4 0",
            "8 0 0 3 0 4 0 0 0",
            "0 7 0 0 0 0 0 9 1");
    private static final int[][] sudokuManySol1 = Sudoku.stringsToGrid(
            "1 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0",
            "0 0 0 0 0 0 0 0 0");
    public static final String sudokuHardModification1 = "3 0 0 0 0 0 0 8 0 0 0 1 0 9 3 0 0 0 0 4 0 7 8 0 0 0 3 0 9 3 8 0 0 0 1 2 0 0 0 0 4 0 0 0 0 5 2 0 0 0 6 7 9 0 6 0 0 0 2 1 0 4 0 0 0 0 5 3 0 9 0 0 0 3 0 0 0 0 0 5 1";
    public static final String wrongSudokuHardModification1 = "3 0 0 0 0 0 0 8 0 0 0 1 0 9 3 0 0 0 0 4 0 7 8 0 0 0 3 0 9 3 8 0 0 0 1 2 0 0 0 0 4 0 0 0 0 5 2 0 0 0 6 7 9 0 6 0 0 0 2 1 0 4 0 0 0 0 5 3 0 9 0 0 0 3 0 0 0 0 0 5";

    protected void setUp() throws Exception {
        sudokuUnsolvable = new Sudoku(sudokuUnsolvable1);
        sudoku1Sol = new Sudoku(sudoku1Sol1);
        sudokuManySol = new Sudoku(sudokuManySol1);
        sudokuMedium =new Sudoku(Sudoku.mediumGrid);
        sudokuEasy = new Sudoku(Sudoku.easyGrid);
        sudokuHard = new Sudoku(Sudoku.mediumGrid);
        sudokuHardModification = new Sudoku(sudokuHardModification1);
    }

    private boolean checkIfAll9(String sol){
        Vector<Integer> tmp = new Vector<>(9);
        for(int i = 0; i < Sudoku.SIZE; i++) {
            tmp.add(0);
        }
        sol = sol.replace(" ", "").replace("\n","");
        for(int i = 0; i < Sudoku.SIZE; i++) {
            for (int j = 0; j < Sudoku.SIZE; j++) {
                char ch = sol.charAt(Sudoku.SIZE * i + j);
                if(ch == '0') return false;
                int index = ch - '0' - 1;
                int value = tmp.get(index);
                tmp.set(index, value + 1);
            }
            for (int k = 0; k < 9; k++) if (tmp.get(k) != i + 1) return false;
        }
        for(int i = 0; i < Sudoku.PART * 3; i++) {
            tmp.set(i, 0);
        }
        for(int i = 0; i < Sudoku.SIZE; i++) {
            for (int j = 0; j < Sudoku.SIZE; j++) {
                char ch = sol.charAt(Sudoku.SIZE * j + i);
                if(ch == '0') return false;
                int index = ch - '0' - 1;
                int value = tmp.get(index);
                tmp.set(index, value + 1);
            }
            for (int k = 0; k < 9; k++) if (tmp.get(k) != i + 1) return false;
        }
        for(int i = 0; i < Sudoku.PART * 3; i++) {
            tmp.set(i, 0);
        }
        for(int i = 0; i < Sudoku.SIZE; i += 3) {
            for (int j = 0; j < Sudoku.PART; j++) {
                int indexx = Sudoku.SIZE * i + j * Sudoku.PART;
                for(int k = 0; k < Sudoku.PART; k++) {
                    for (int m = 0; m < Sudoku.PART; m++) {
                        char ch = sol.charAt(indexx + k * Sudoku.SIZE + m);
                        if(ch == '0') return false;
                        int index = ch - '0' - 1;
                        int value = tmp.get(index);
                        tmp.set(index, value + 1);
                    }
                }
                for (int k = 0; k < 9; k++) if (tmp.get(k) != (i + j + 1)) return false;
            }
        }
        return true;
    }
    public void testSolve() {
        int cnt = sudokuManySol.solve();
        assertEquals(cnt,100);
        assertTrue(checkIfAll9(sudokuManySol.getSolutionText()));
        cnt = sudokuUnsolvable.solve();
        assertEquals(cnt,0);
        cnt = sudoku1Sol.solve();
        assertEquals(cnt,1);
        assertTrue(checkIfAll9(sudoku1Sol.getSolutionText()));
        cnt = sudokuEasy.solve();
        assertEquals(cnt,1);
        assertTrue(checkIfAll9(sudokuEasy.getSolutionText()));
        cnt = sudokuMedium.solve();
        assertEquals(cnt,1);
        assertTrue(checkIfAll9(sudokuMedium.getSolutionText()));
        cnt = sudokuHard.solve();
        assertEquals(cnt,1);
        assertTrue(checkIfAll9(sudokuHard.getSolutionText()));
        cnt = sudokuHardModification.solve();
        assertEquals(cnt,6);
        assertTrue(checkIfAll9(sudokuHardModification.getSolutionText()));
    }

    public void testOther(){
        int cnt = sudokuHardModification.solve();
        assertTrue(sudokuHardModification.toString().replace(" ", "").replace("\n","").equals(sudokuHardModification1.replace(" ","")));
        String[] args = new String[1];
        sudokuHardModification.main(args);
        cnt = sudokuManySol.solve();
        sudokuManySol.main(args);
    }
}
