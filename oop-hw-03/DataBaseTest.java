import junit.framework.TestCase;

import java.sql.*;
import java.util.Vector;

public class DataBaseTest extends TestCase {
    private DataBase dataBase;
    static String databaseTest = "metropolises";
    private Connection con;
    private Connection conTst;
    private ResultSet set;
    protected void setUp() throws Exception {
        dataBase = new DataBase();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + DataBase.server;
            con = DriverManager.getConnection(url, DataBase.account, DataBase.password);
            Statement st = con.createStatement();
            st.executeUpdate("USE " + databaseTest);
            st.executeUpdate("DROP TABLE IF EXISTS metropolises");
            st.executeUpdate("CREATE TABLE metropolises (metropolis CHAR(64), continent CHAR(64), population BIGINT);");
            st.executeUpdate("INSERT INTO metropolises VALUES (\"Mumbai\",\"Asia\",20400000), (\"New York\",\"North America\",21295000), (\"San Francisco\",\"North America\",5780000), (\"London\",\"Europe\",8580000), (\"Rome\",\"Europe\",2715000), (\"Melbourne\",\"Australia\",3900000), (\"San Jose\",\"North America\",7354555), (\"Rostov-on-Don\",\"Europe\",1052000);");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public void testAdd(){
        dataBase.add("Tokyo", "Asia", "37194000");
        try{
            Statement st = con.createStatement();
            set = st.executeQuery("SELECT * FROM metropolises WHERE metropolis = 'Tokyo' AND continent = 'Asia' AND population = '37194000'");
            set.next();
            assertEquals(37194000, set.getInt(3));
            assertEquals("Tokyo", set.getString(1));
            assertEquals("Asia", set.getString(2));

        } catch (SQLException e) {
            System.out.println("ragaciseveraa");
        }
        dataBase.add("Metro", "Earth", "1234567");
        try{
            Statement st = con.createStatement();
            set = st.executeQuery("SELECT * FROM metropolises WHERE metropolis = 'Metro' AND continent = 'Earth' AND population = '1234567'");
            set.next();
            assertEquals(1234567, set.getInt(3));
            assertEquals("Metro", set.getString(1));
            assertEquals("Earth", set.getString(2));

        } catch (SQLException e) {
            System.out.println("ragaciseveraa");
        }
        dataBase.add("Whole", "World", "8032134772");
        try{
            Statement st = con.createStatement();
            set = st.executeQuery("SELECT * FROM metropolises WHERE metropolis = 'Whole' AND continent = 'World' AND population = '8032134772'");
            set.next();
            assertEquals("8032134772", set.getString(3));
            assertEquals("Whole", set.getString(1));
            assertEquals("World", set.getString(2));

        } catch (SQLException e) {
            System.out.println("ragaciseveraa");
        }
        dataBase.add("Axla", "Chavamate", "5000000000");
        try{
            Statement st = con.createStatement();
            set = st.executeQuery("SELECT * FROM metropolises WHERE metropolis = 'Axla' AND continent = 'Chavamate' AND population = '5000000000'");
            set.next();
            assertEquals("5000000000", set.getString(3));
            assertEquals("Axla", set.getString(1));
            assertEquals("Chavamate", set.getString(2));

        } catch (SQLException e) {
            System.out.println("ragaciseveraa");
        }
        dataBase.add("without", "population", "");
        try{
            Statement st = con.createStatement();
            set = st.executeQuery("SELECT * FROM metropolises WHERE metropolis = 'without' AND continent = 'population' AND population = '0'");
            set.next();
            assertEquals("0", set.getString(3));
            assertEquals("without", set.getString(1));
            assertEquals("population", set.getString(2));

        } catch (SQLException e) {
            System.out.println("ragaciseveraa");
        }
    }

    public void testSearch(){
        set = dataBase.search("Mumbai", "Asia", "204", true, true);
        try {
            set.next();
            assertEquals("20400000", set.getString(3));
            assertEquals("Mumbai", set.getString(1));
            assertEquals("Asia", set.getString(2));
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }

        set = dataBase.search("New York", "North America", "21295000", true, true);
        try {
            set.next();
            assertEquals("21295000", set.getString(3));
            assertEquals("New York", set.getString(1));
            assertEquals("North America", set.getString(2));
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }

        set = dataBase.search("San", "Nor", "578000", true, false);
        try {
            set.next();
            assertEquals("5780000", set.getString(3));
            assertEquals("San Francisco", set.getString(1));
            assertEquals("North America", set.getString(2));
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }

        set = dataBase.search("San", "Nor", "57800000", false, false);
        try {
            set.next();
            assertEquals("5780000", set.getString(3));
            assertEquals("San Francisco", set.getString(1));
            assertEquals("North America", set.getString(2));
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }

        set = dataBase.search("San Francisco", "North America", "578000000", false, true);
        try {
            set.next();
            assertEquals("5780000", set.getString(3));
            assertEquals("San Francisco", set.getString(1));
            assertEquals("North America", set.getString(2));
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }
        set = dataBase.search("San Francisco", "", "578000000", false, true);
        try {
            set.next();
            assertEquals("5780000", set.getString(3));
            assertEquals("San Francisco", set.getString(1));
            assertEquals("North America", set.getString(2));
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }

        set = dataBase.search("", "North America", "578000000", false, true);
        try {
            boolean bool = false;
            while(set.next()){
                if("San Francisco".equals(set.getString(1))){
                    bool = true;
                }
            }
            assertTrue(bool);
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }

        set = dataBase.search("San Francisco", "", "", false, true);
        try {
            boolean bool = false;
            while(set.next()){
                if("San Francisco".equals(set.getString(1))){
                    bool = true;
                }
            }
            assertTrue(bool);
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }
        set = dataBase.search("", "North America", "", false, true);
        try {
            boolean bool = false;
            while(set.next()){
                if("San Francisco".equals(set.getString(1))){
                    bool = true;
                }
            }
            assertTrue(bool);
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }
        set = dataBase.search("", "", "578000000", false, true);
        try {
            boolean bool = false;
            while(set.next()){
                if("San Francisco".equals(set.getString(1))){
                    bool = true;
                }
            }
            assertTrue(bool);
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }
        set = dataBase.search("", "", "57800", true, false);
        try {
            boolean bool = false;
            while(set.next()){
                if("San Francisco".equals(set.getString(1))){
                    bool = true;
                }
            }
            assertTrue(bool);
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }
        set = dataBase.search("San", "", "", true, false);
        try {
            boolean bool = false;
            while(set.next()){
                if("San Francisco".equals(set.getString(1))){
                    bool = true;
                }
            }
            assertTrue(bool);
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }
        set = dataBase.search("San", "", "", false, false);
        try {
            boolean bool = false;
            while(set.next()){
                if("San Francisco".equals(set.getString(1))){
                    bool = true;
                }
            }
            assertTrue(bool);
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }
        set = dataBase.search("San", "", "580000000", false, false);
        try {
            boolean bool = false;
            while(set.next()){
                if("San Francisco".equals(set.getString(1))){
                    bool = true;
                }
            }
            assertTrue(bool);
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }
        set = dataBase.search("San Francisco", "", "58000", true, true);
        try {
            boolean bool = false;
            while(set.next()){
                if("San Francisco".equals(set.getString(1))){
                    bool = true;
                }
            }
            assertTrue(bool);
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }
        set = dataBase.search("San", "Nor", "", false, false);
        try {
            boolean bool = false;
            while(set.next()){
                if("San Francisco".equals(set.getString(1))){
                    bool = true;
                }
            }
            assertTrue(bool);
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }
        set = dataBase.search("San", "", "580", true, false);
        try {
            boolean bool = false;
            while(set.next()){
                if("San Francisco".equals(set.getString(1))){
                    bool = true;
                }
            }
            assertTrue(bool);
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }

        set = dataBase.search("", "North America", "580", true, true);
        try {
            boolean bool = false;
            while(set.next()){
                if("San Francisco".equals(set.getString(1))){
                    bool = true;
                }
            }
            assertTrue(bool);
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }

        set = dataBase.search("", "North Am", "580", true, false);
        try {
            boolean bool = false;
            while(set.next()){
                if("San Francisco".equals(set.getString(1))){
                    bool = true;
                }
            }
            assertTrue(bool);
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }
        set = dataBase.search("", "North Am", "58000000000", false, false);
        try {
            boolean bool = false;
            while(set.next()){
                if("San Francisco".equals(set.getString(1))){
                    bool = true;
                }
            }
            assertTrue(bool);
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }
        set = dataBase.search("", "North Am", "", true, false);
        try {
            boolean bool = false;
            while(set.next()){
                if("San Francisco".equals(set.getString(1))){
                    bool = true;
                }
            }
            assertTrue(bool);
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }

        set = dataBase.search("", "", "", true, false);
        try {
            boolean bool = false;
            while(set.next()){
                if("San Francisco".equals(set.getString(1))){
                    bool = true;
                }
            }
            assertTrue(bool);
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }
    }

    public void testAddSearch(){
        dataBase.add("Tokyo", "Asia", "37194000");
        dataBase.add("Metro", "Earth", "1234567");
        dataBase.add("Whole", "World", "8032134772");
        dataBase.add("Axla", "Chavamate", "5000000000");
        dataBase.add("without", "population", "");
        set = dataBase.search("Me", "Eart", "", true, false);
        try {
            boolean bool = false;
            while(set.next()){
                if("Metro".equals(set.getString(1))){
                    bool = true;
                    break;
                }
            }
            assertTrue(bool);
            assertEquals(1234567, set.getInt(3));
            assertEquals("Metro", set.getString(1));
            assertEquals("Earth", set.getString(2));
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }

        set = dataBase.search("Axla", "", "5000000000", true, false);
        try {
            boolean bool = false;
            while(set.next()){
                if("Axla".equals(set.getString(1))){
                    bool = true;
                    break;
                }
            }
            assertTrue(bool);
            assertEquals("5000000000", set.getString(3));
            assertEquals("Axla", set.getString(1));
            assertEquals("Chavamate", set.getString(2));
        } catch (SQLException e) {
            System.out.println("ragaciseveraa2");
        }
    }
}


