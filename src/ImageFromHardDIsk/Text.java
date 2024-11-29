//package ImageFromHardDIsk;

//package pkg;  // Package declaration

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Text {
    private Connection con;

    // Constructor to set up the connection
    public Text() {
        String url = "jdbc:oracle:thin:@sola.uc.ac.kr:10:xe"; // Correct format for Oracle's JDBC URL
        String userid = "s2312075";
        String pwd = "11";

        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load the Oracle JDBC driver.");
            e.printStackTrace();
            return; // Exit constructor if driver load fails
        }

        try {
            // Establish database connection
            System.out.println("Preparing to connect to the database...");
            con = DriverManager.getConnection(url, userid, pwd);
            System.out.println("Database connected successfully");
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }

    // Method to run SQL query
    public void sqlRun() {
        String query = "SELECT * FROM CAR";  // SQL query to retrieve data from the "text" table
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);

            // Print the results of the query
            System.out.println("ID \t Name \t\t SSN \t\t Address \t\t Nationality \t Position \t\t Photo");
            while (rs.next()) {
                System.out.print(rs.getInt("id") + "\t"); // ID column
                System.out.print(rs.getString("name") + "\t"); // Name column
                System.out.print(rs.getString("ssn") + "\t"); // SSN column
                System.out.print(rs.getString("address") + "\t"); // Address column
                System.out.print(rs.getString("nationality") + "\t"); // Nationality column
                System.out.print(rs.getString("position") + "\t"); // Position column

                // Assuming the "photo" column stores binary data
                byte[] photo = rs.getBytes("photo");
                if (photo != null) {
                    System.out.println("[PHOTO AVAILABLE]");
                } else {
                    System.out.println("[NO PHOTO]");
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL query failed.");
            e.printStackTrace();
        }
    }
    public ResultSet sqlRun(String query) throws SQLException {
        Statement stmt = con.createStatement();
        return stmt.executeQuery(query);
    }
    // Close connection
    public void closeConnection() throws SQLException {
        if (con != null && !con.isClosed()) {
            con.close();
            System.out.println("Database connection closed.");
        }
    }


    // Main method
    public static void main(String[] args) {
        Text so = new Text();  // Instantiate the Text class
        if (so.con != null) {  // Check if the connection was successful
            so.sqlRun();       // Run the SQL query method
            // Close the connection after running the query
            try {
                if (so.con != null && !so.con.isClosed()) {
                    so.con.close();
                    System.out.println("Database connection closed.");
                }
            } catch (SQLException e) {
                System.err.println("Failed to close the database connection.");
                e.printStackTrace();
            }
        }

    }
}

