import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBaseJframe extends JFrame {

    private JTextField metropolisTextField;
    private JTextField continentTextField;
    private JTextField populationTextField;
    private JButton searchButton;
    private JButton addButton;
    private JComboBox<String> pulldown1;
    private JComboBox<String> pulldown2;
    private DefaultTableModel tableModel;

    private DataBase database;

    public DataBaseJframe() {
        setTitle("Metropolis viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);

        JPanel inputPanel = new JPanel();
        add(inputPanel, BorderLayout.NORTH);

        inputPanel.add(new JLabel("Metropolis:"));
        metropolisTextField = new JTextField();
        metropolisTextField.setColumns(10);
        inputPanel.add(metropolisTextField);

        inputPanel.add(new JLabel("Continent:"));
        continentTextField = new JTextField();
        continentTextField.setColumns(10);
        inputPanel.add(continentTextField);

        inputPanel.add(new JLabel("Population:"));
        populationTextField = new JTextField();
        populationTextField.setColumns(10);
        inputPanel.add(populationTextField);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());
        add(searchPanel, BorderLayout.EAST);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1;
        gbc.weighty = 0;

        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                database.search(metropolisTextField.getText(), continentTextField.getText(), populationTextField.getText(), pulldown1.getSelectedItem().equals("Population larger than"), pulldown2.getSelectedItem().equals("Exact match                    "));
            }
        });
        searchPanel.add(searchButton, gbc);

        gbc.gridy++;
        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                database.add(metropolisTextField.getText(), continentTextField.getText(), populationTextField.getText());
            }
        });
        searchPanel.add(addButton, gbc);

        gbc.gridy++;
        String[] items1 = {"Population larger than", "Population smaller than"};
        pulldown1 = new JComboBox<>(items1);
        searchPanel.add(pulldown1, gbc);

        gbc.gridy++;
        String[] items2 = {"Exact match                    ", "Substrings"};
        pulldown2 = new JComboBox<>(items2);
        searchPanel.add(pulldown2, gbc);

        gbc.gridy++;
        gbc.weighty = 1;
        searchPanel.add(new JLabel(""), gbc);

        database = new DataBase();
        JTable table = new JTable(database);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
            DataBaseJframe frame = new DataBaseJframe();
            frame.setVisible(true);
    }
}
