import java.sql.*;
import javax.swing.table.AbstractTableModel;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import org.apache.commons.dbcp2.BasicDataSource;

public class DataBase extends AbstractTableModel{
    public static String account = "root";
    public static String password = "";
    public static String server = "localhost";
    public static String database = "metropolises";

    private Connection con;

    private ResultSet set;
    public DataBase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + server + ":3306/" + database;
            con = DriverManager.getConnection(url, account, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void add(String metropolis, String continent, String population){
        set = null;
        try {
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO metropolises (metropolis, continent, population) VALUES (?, ?, ?)");
            preparedStatement.setString(1, metropolis);
            preparedStatement.setString(2, continent);
            if(!population.equals("")) {
                preparedStatement.setLong(3, Long.parseLong(population));
            } else {
                preparedStatement.setLong(3, 0);
            }
            preparedStatement.executeUpdate();
            search(metropolis, continent, population, false, true);
            fireTableDataChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    String searchStatement(String metropolis, String continent, String population, boolean larger, boolean exact){
        String statement = "SELECT * FROM metropolises";
        if(!metropolis.equals("")  && !population.equals("") && !continent.equals("")){
            if(larger && exact) {
                statement = "SELECT * FROM metropolises WHERE metropolis = ? AND continent = ? AND population >= ?";
            } else if(larger){
                statement = "SELECT * FROM metropolises WHERE metropolis LIKE ? AND continent LIKE ? AND population >= ?";
            } else if(exact){
                statement = "SELECT * FROM metropolises WHERE metropolis = ? AND continent = ? AND population <= ?";
            } else {
                statement = "SELECT * FROM metropolises WHERE metropolis LIKE ? AND continent LIKE ? AND population <= ?";
            }
            return statement;
        }
        if(!metropolis.equals("") && !continent.equals("")){
            if(exact){
                statement += " WHERE metropolis = ? AND continent = ?";
            } else {
                statement += " WHERE metropolis LIKE ? AND continent LIKE ?";
            }
            return statement;
        }
        if(!metropolis.equals("") && !population.equals("")){
            if(exact && larger){
                statement += " WHERE metropolis = ? AND population >= ?";
            } else if(exact){
                statement += " WHERE metropolis = ? AND population <= ?";
            } else if(larger){
                statement += " WHERE metropolis LIKE ? AND population >= ?";
            } else {
                statement += " WHERE metropolis LIKE ? AND population <= ?";
            }
            return statement;
        }
        if(!continent.equals("") && !population.equals("")){
            if(exact && larger){
                statement += " WHERE continent = ? AND population >= ?";
            } else if(exact){
                statement += " WHERE continent = ? AND population <= ?";
            } else if(larger){
                statement += " WHERE continent LIKE ? AND population >= ?";
            } else {
                statement += " WHERE continent LIKE ? AND population <= ?";
            }
            return statement;
        }

        if(!metropolis.equals("")){
            if(exact){
                statement += " WHERE metropolis = ?";
            } else {
                statement += " WHERE metropolis LIKE ?";
            }
            return statement;
        }

        if(!continent.equals("")){
            if(exact){
                statement += " WHERE continent = ?";
            } else {
                statement += " WHERE continent LIKE ?";
            }
            return statement;
        }
        if(!population.equals("")){
            if(larger){
                statement += " WHERE population >= ?";
            } else {
                statement += " WHERE population <= ?";
            }
            return statement;
        }
        return statement;
    }

    public ResultSet search(String metropolis, String continent, String population, boolean larger, boolean exact){
        set = null;
        try {
            String statement = searchStatement(metropolis, continent, population, larger, exact);
            PreparedStatement preparedStatement = con.prepareStatement(statement);
            int paramIndex = 1;
            if (!metropolis.isEmpty()) {
                if(exact) {
                    preparedStatement.setString(paramIndex++, metropolis);
                } else {
                    preparedStatement.setString(paramIndex++, "%" + metropolis + "%");
                }
            }
            if (!continent.isEmpty()) {
                if(exact) {
                    preparedStatement.setString(paramIndex++, continent);
                } else {
                    preparedStatement.setString(paramIndex++, "%" + continent + "%");
                }
            }
            if (!population.isEmpty()) {
                preparedStatement.setLong(paramIndex, Long.parseLong(population));
            }
            set = preparedStatement.executeQuery();
            fireTableDataChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return set;
    }

    @Override
    public int getRowCount() {
        if(set == null) return 0;
        try {
            set.last();
            return set.getRow();
        } catch(SQLException e) {
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(set == null) return "";
        try{
            set.first();
            set.relative(rowIndex);
            if(columnIndex + 1 == 1) return set.getString(columnIndex + 1);
            else if(columnIndex + 1 == 2) return set.getString(columnIndex + 1);
            else return set.getString(columnIndex + 1);
        } catch (SQLException e) {
            return "";
        }
    }
    @Override
    public String getColumnName(int columnIndex) {
        String[] cols = { "Metropolis", "Continent", "Population" };
        return cols[columnIndex];
    }
}
