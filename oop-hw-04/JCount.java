// JCount.java

/*
 Basic GUI/Threading exercise.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JCount extends JPanel {
	private JTextField textField;
	private JLabel label;
	private JButton start;
	private JButton stop;
	private workerThread worker;

	public JCount() {
		super();
		// Set the JCount to use Box layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// YOUR CODE HERE
		worker = null;
		textField = new JTextField(20);
		label = new JLabel("0");
		start = new JButton("start");
		stop = new JButton("stop");

		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(worker != null){
					worker.interrupt();
				}
				worker = new workerThread();
				worker.start();
			}
		});
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(worker != null) {
					worker.interrupt();
					worker = null;
				}
			}
		});
		add(textField);
		add(label);
		add(start);
		add(stop);
		add(Box.createRigidArea(new Dimension(0,40)));
	}

	public class workerThread extends Thread{
		@Override
		public void run() {
			int i = 0;
			while (i <= Integer.parseInt(textField.getText())){
				if(i % 10000 == 0){
					try {
						Thread.sleep(100);
					} catch (InterruptedException e){break;}
					final int k = i;
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							label.setText("" + k);
						}
					});
				}
				i++;
			}
		}
	}

	private static void createAndShowGUI(){
		JFrame frame = new JFrame("The Count");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	static public void main(String[] args)  {
		// Creates a frame with 4 JCounts in it.
		// (provided)
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}
}

