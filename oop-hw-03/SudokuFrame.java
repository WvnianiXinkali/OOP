import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.text.StyledEditorKit;

import java.awt.*;
import java.awt.event.*;


 public class SudokuFrame extends JFrame {
	private JTextArea sourcePuzzleText;
	 private JTextArea resultText;
	 private  Checkbox checkbox;
	 private  Box box;
	 private JButton button;
	 private void solvePuzzle(){
		 Sudoku sudoku;
		 try {
			 sudoku = new Sudoku(Sudoku.textToGrid(sourcePuzzleText.getText()));
		 } catch (Exception ignored) {
			 resultText.setText("Parsing Problem");
			 return;
		 }
		 int solCnt = sudoku.solve();
		 if(solCnt >= 1) {
			 resultText.setText(sudoku.getSolutionText() + "\n" + "Solutions: " + solCnt + "\n" + "Elapsed: " + sudoku.getElapsed());
		 } else {
			 resultText.setText("No Solutions For Current Sudoku" + "\n" + "Elapsed: " + sudoku.getElapsed());
		 }
	 }
	public SudokuFrame() {
		super("Sudoku Solver");

		// YOUR CODE HERE
		setLayout(new BorderLayout(4, 4));

		sourcePuzzleText = new JTextArea(15, 20);
		sourcePuzzleText.setBorder(new TitledBorder("Puzzle"));
		add(sourcePuzzleText, BorderLayout.WEST);
		resultText = new JTextArea(15, 20);
		resultText.setBorder(new TitledBorder("Solution"));
		add(resultText, BorderLayout.EAST);
		box = Box.createHorizontalBox();
		button = new JButton("Check");
		checkbox = new Checkbox("Auto Check");
		box.add(button);
		box.add(checkbox);
		add(box, BorderLayout.SOUTH);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				solvePuzzle();
			}
		});

		checkbox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				solvePuzzle();
			}
		});
		sourcePuzzleText.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				solvePuzzle();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				solvePuzzle();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				solvePuzzle();
			}
		});

		setLocationByPlatform(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		SudokuFrame frame = new SudokuFrame();
		
	}

}
