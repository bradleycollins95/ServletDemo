import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDBConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/books?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root";
        String password = "1qaz2w";

        try {
            //explicitly load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //attempt connection
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connection successful!");

        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL JDBC Driver not found. Install it properly.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Database connection failed!");
            e.printStackTrace();
        }
    }
}

