import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://db:5432/testdb";
        String user = "postgres";
        String password = "root";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            // Create table if not exists
            Statement st = conn.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id SERIAL PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "email VARCHAR(100))");

            String sql = "INSERT INTO users(name, email) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "rohit");
            stmt.setString(2, "rohit@gmail.com");

            stmt.executeUpdate();
            System.out.println("Record Inserted Successfully!");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}