package task2;


import java.sql.*;
import java.util.ArrayList;

public class StoreController {
    public static String account = "root";
    public static String password = "";
    public static String server = "localhost";
    public static String database = "products";

    private Connection con;

    private ResultSet set;

    public StoreController(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + server + ":3306/" + database;
            con = DriverManager.getConnection(url, account, password);
        } catch (SQLException e) {
            System.out.println("kk");
        } catch (ClassNotFoundException e) {
            System.out.println("pp");
        }
    }

    public ResultSet getDataBase(){
        try {
            String query = "SELECT * FROM products";
            set = con.createStatement().executeQuery(query);
        } catch (SQLException e) {
            System.out.println("meme");
        }
        return set;
    }

    public ArrayList<String> getRow(String id) throws SQLException {
        ArrayList<String> arr = new ArrayList<>();
        Statement stm = con.createStatement();
        ResultSet res = stm.executeQuery("SELECT * FROM products WHERE productid = '" + id + "';");
        if(res.next()) {
            arr.add(res.getString(2));
            arr.add(Double.toString(res.getDouble(4)));
            arr.add(res.getString(3));
        }
        return arr;
    }
}
