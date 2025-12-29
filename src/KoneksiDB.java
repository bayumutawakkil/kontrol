import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Class & Object]
// JDBC Connection Management]
public class KoneksiDB {
    
    // Encapsulation
    private static final String URL = "jdbc:mysql://localhost:3307/kas_organisasi_db";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Sesuaikan dengan konfigurasi MySQL Anda
    
    // Implementasi JDBC
    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            // Penanganan Exception
            throw new SQLException("Driver MySQL tidak ditemukan!", e);
        }
    }
    
    // Method untuk test koneksi
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Gagal koneksi ke database: " + e.getMessage());
            return false;
        }
    }
}
