package CustomerPack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/new_database1?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "welcome1";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
    }

    public static void ensureTableExists() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS customers (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) NOT NULL, " +
                    "phone VARCHAR(15) NOT NULL, " +
                    "address TEXT NOT NULL, " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            stmt.execute(createTableQuery);
            System.out.println("Table 'customers' is ensured to exist.");
        } catch (Exception e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }
}
