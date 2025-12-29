package app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Kelas DatabaseHelper
 * Tujuan: Mengelola Koneksi Database menggunakan JDBC
 * 
 * [Soal 2.a] Class & Object: Ini adalah kelas helper untuk koneksi database
 * [Soal 2.h] Implementasi JDBC: Membuat koneksi MySQL
 */
public class DatabaseHelper {
    
    // Konfigurasi Database
    private static final String URL = "jdbc:mysql://localhost:3307/kontrol_db";
    private static final String USER = "root";  // Sesuaikan dengan pengaturan MySQL Anda
    private static final String PASSWORD = "";  // Sesuaikan dengan pengaturan MySQL Anda
    
    /**
     * [Soal 2.a] Constructor
     * Constructor private untuk mencegah instansiasi
     */
    private DatabaseHelper() {
        // Kelas utilitas - tidak perlu instansiasi
    }
    
    /**
     * [Soal 2.h] Metode Koneksi JDBC
     * Membuat dan mengembalikan koneksi ke database MySQL
     * 
     * @return Objek Connection
     * @throws SQLException jika koneksi gagal
     */
    public static Connection getConnection() throws SQLException {
        try {
            // [Soal 2.g] Exception Handling: Menangani ClassNotFoundException
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            // [Soal 2.g] Exception Handling: Menangkap dan membungkus ClassNotFoundException
            throw new SQLException("MySQL JDBC Driver tidak ditemukan!", e);
        }
    }
    
    /**
     * Tes koneksi database
     * 
     * @return true jika koneksi berhasil, false jika gagal
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            // [Soal 2.g] Exception Handling: Penanganan SQLException
            System.err.println("Koneksi database gagal: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Menutup koneksi database dengan aman
     * 
     * @param conn Koneksi yang akan ditutup
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // [Soal 2.g] Exception Handling: Penanganan SQLException
                System.err.println("Error menutup koneksi: " + e.getMessage());
            }
        }
    }
}
