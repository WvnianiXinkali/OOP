import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class WebFrame  extends JFrame{
    private DefaultTableModel model;
    private JTable table;
    private JPanel panel;
    private JButton button;
    private JButton button2;
    private JButton button3;
    private JTextField textField;
    private JLabel label;
    private JLabel label1;
    private JLabel label2;
    private JProgressBar progressBar;
    private int progress;
    private int urlCount;
    private Semaphore semaphore;
    private int runningThreads;
    private CountDownLatch countDownLatch;
    private launcher launch;
    private ArrayList<WebWorker> workers;

    private int k;

    public WebFrame(){
        JFrame frame = new JFrame("Web loader");
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        runningThreads = 0;
        panel = new JPanel();
        model = new DefaultTableModel(new String[] {"url", "status"}, 0);
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600,300));
        panel.add(scrollPane);

        button = new JButton("Single Thread Fetch");
        button2 = new JButton("Concurrent Fetch");
        button3 = new JButton("Stop");
        button3.setEnabled(false);
        label = new JLabel("Running:0");
        label1 = new JLabel("Completed:0");
        label2 = new JLabel("Elapsed:0");

        textField = new JTextField(10);
        textField.setMaximumSize(new Dimension(40, 20));

        progressBar = new JProgressBar();

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                semaphore = new Semaphore(1);
                countDownLatch = new CountDownLatch(urlCount);
                k = 1;
                runningState();
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countDownLatch = new CountDownLatch(urlCount);
                semaphore = new Semaphore(Integer.parseInt(textField.getText()));
                k = Integer.parseInt(textField.getText());
                runningState();
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(launch != null)
                    launch.interrupt();
                readyState();
            }
        });

        frame.add(panel);
        frame.add(button);
        frame.add(button2);
        frame.add(textField);
        frame.add(label);
        frame.add(label1);
        frame.add(label2);
        frame.add(progressBar);
        frame.add(button3);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void runningState(){
        button.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(true);
        label.setText("Running:0");
        label1.setText("Completed:0");
        label2.setText("Elapsed:0");
        progressBar.setMaximum(urlCount);
        workers = new ArrayList<>();
        for(int i = 0; i < urlCount; i ++){
            model.setValueAt("", i,1);
        }
        launch = new launcher(this);
        launch.start();
    }

    private void readyState(){
        button.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(false);
        progress = 0;
        progressBar.setValue(progress);
    }

    public void updateScreenSuccesful(long milliseconds, int bytes, int rowNumber){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Date date = new Date();
                String  toSet = "" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + " " + milliseconds + "ms " + bytes + " bytes";
                model.setValueAt(toSet, rowNumber, 1);
            }
        });
    }

    public void updateScreenMallformed(int rowNumber){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                model.setValueAt("err", rowNumber, 1);
            }
        });
    }
    public void updateScreenInterrupted(int rowNumber){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                model.setValueAt("Interrupted", rowNumber, 1);
            }
        });
    }

    public synchronized void changeRunningThreadCnt(boolean incDec){
        if(incDec)
            runningThreads++;
        else {
            runningThreads--;
            progress++;
            if(progress > urlCount) progress = urlCount;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    progressBar.setValue(progress);
                    label1.setText("completed:" + progress);
                }
            });
        }

        label.setText("Running:" + runningThreads);
    }
    public void postSemaphore(){
        semaphore.release();
    }

    public void postCountdown(){
        countDownLatch.countDown();
    }


    private class launcher extends Thread {
        private WebFrame webFrame;

        public launcher(WebFrame webFrame){
            this.webFrame = webFrame;
        }
        @Override
        public void run(){
            webFrame.changeRunningThreadCnt(true);
            long started = System.currentTimeMillis();

            for(int i = 0; i < urlCount; i++){
                if(isInterrupted()) break;

                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    for(int b = 0; b < urlCount - countDownLatch.getCount() - (k - semaphore.availablePermits()); b++) countDownLatch.countDown();
                    for(WebWorker worker: workers){ worker.interrupt(); }
                    break;
                }

                WebWorker worker = new WebWorker((String) model.getValueAt(i, 0), i, webFrame);
                worker.start();
                workers.add(worker);
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                for(WebWorker worker: workers){ worker.interrupt(); }
            }
            webFrame.changeRunningThreadCnt(false);
            long ended = System.currentTimeMillis();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    webFrame.readyState();
                    double time = ((double)(ended - started)) / 1000;
                    label2.setText("Elapsed:" + (time));
                }
            });
        }
    }

    private void readFileAndShow(String file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            while (true) {
                line = reader.readLine();
                if(line == null) break;
                model.addRow(new Object[]{line});
            }
            urlCount = model.getRowCount();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void createAndShowGUI(String file){
        WebFrame webFrame = new WebFrame();
        webFrame.readFileAndShow(file);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(args[0]);
            }
        });
    }
}
