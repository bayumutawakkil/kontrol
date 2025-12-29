package app;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Kelas TransactionDAO
 * Tujuan: Mengimplementasikan operasi CRUD untuk transaksi keuangan
 * 
 * [Soal 2.c] Implementasi Interface: Mengimplementasikan interface FinancialDataDAO
 * [Soal 2.h] JDBC & CRUD: Implementasi lengkap operasi database
 * [Soal 2.h] Collection Framework: Menggunakan ArrayList untuk menyimpan data
 */
public class TransactionDAO implements FinancialDataDAO {
    
    /**
     * [Soal 2.h] Implementasi CRUD - CREATE
     * [Soal 2.h] JDBC: Menggunakan PreparedStatement untuk insert data
     * 
     * @param transaction Objek transaksi yang akan ditambahkan
     * @return true jika berhasil, false jika gagal
     * @throws SQLException jika terjadi error database
     */
    @Override
    public boolean addTransaction(Transaction transaction) throws SQLException {
        // [Soal 2.h] JDBC: Statement SQL INSERT
        String sql = "INSERT INTO transactions (type, amount, description, date) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // [Soal 2.h] JDBC: Set parameter menggunakan PreparedStatement
            pstmt.setString(1, transaction.getType());
            pstmt.setDouble(2, transaction.getAmount());
            pstmt.setString(3, transaction.getDescription());
            pstmt.setDate(4, Date.valueOf(transaction.getDate()));
            
            // [Soal 2.h] Eksekusi query INSERT
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            // [Soal 2.g] Exception Handling: Penanganan SQLException
            System.err.println("Error menambahkan transaksi: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * [Soal 2.h] Implementasi CRUD - READ (Semua)
     * [Soal 2.h] Collection Framework: Mengembalikan ArrayList<Transaction>
     * [Soal 2.h] JDBC: Menggunakan ResultSet untuk mengambil data
     * 
     * @return List berisi semua transaksi
     * @throws SQLException jika terjadi error database
     */
    @Override
    public List<Transaction> getAllTransactions() throws SQLException {
        // [Soal 2.h] Collection Framework: ArrayList untuk menyimpan transaksi
        List<Transaction> transactions = new ArrayList<>();
        
        // [Soal 2.h] JDBC: Statement SQL SELECT
        String sql = "SELECT * FROM transactions ORDER BY date DESC, id DESC";
        
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // [Soal 2.e] Loop: Iterasi melalui ResultSet
            while (rs.next()) {
                // [Soal 2.h] JDBC: Ekstrak data dari ResultSet
                int id = rs.getInt("id");
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                String description = rs.getString("description");
                LocalDate date = rs.getDate("date").toLocalDate();
                
                // [Soal 2.d] Polymorphism: Membuat objek subclass yang sesuai
                Transaction transaction;
                if ("INCOME".equalsIgnoreCase(type)) {
                    transaction = new Income(id, amount, description, date);
                } else {
                    transaction = new Expense(id, amount, description, date);
                }
                
                // [Soal 2.h] Collection Framework: Tambahkan ke ArrayList
                transactions.add(transaction);
            }
            
        } catch (SQLException e) {
            // [Soal 2.g] Exception Handling: Penanganan SQLException
            System.err.println("Error mengambil transaksi: " + e.getMessage());
            throw e;
        }
        
        return transactions;
    }
    
    /**
     * [Soal 2.h] Implementasi CRUD - READ (By ID)
     * 
     * @param id ID Transaksi
     * @return Objek Transaction atau null jika tidak ditemukan
     * @throws SQLException jika terjadi error database
     */
    @Override
    public Transaction getTransactionById(int id) throws SQLException {
        // [Soal 2.h] JDBC: SQL SELECT dengan klausa WHERE
        String sql = "SELECT * FROM transactions WHERE id = ?";
        
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String type = rs.getString("type");
                    double amount = rs.getDouble("amount");
                    String description = rs.getString("description");
                    LocalDate date = rs.getDate("date").toLocalDate();
                    
                    // [Soal 2.d] Polymorphism: Mengembalikan subclass yang sesuai
                    if ("INCOME".equalsIgnoreCase(type)) {
                        return new Income(id, amount, description, date);
                    } else {
                        return new Expense(id, amount, description, date);
                    }
                }
            }
            
        } catch (SQLException e) {
            // [Soal 2.g] Exception Handling: Penanganan SQLException
            System.err.println("Error mengambil transaksi berdasarkan ID: " + e.getMessage());
            throw e;
        }
        
        return null;
    }
    
    /**
     * [Soal 2.h] Implementasi CRUD - UPDATE
     * [Soal 2.h] JDBC: Menggunakan PreparedStatement untuk update data
     * 
     * @param transaction Objek transaksi dengan data yang diperbarui
     * @return true jika berhasil, false jika gagal
     * @throws SQLException jika terjadi error database
     */
    @Override
    public boolean updateTransaction(Transaction transaction) throws SQLException {
        // [Soal 2.h] JDBC: Statement SQL UPDATE
        String sql = "UPDATE transactions SET type = ?, amount = ?, description = ?, date = ? WHERE id = ?";
        
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, transaction.getType());
            pstmt.setDouble(2, transaction.getAmount());
            pstmt.setString(3, transaction.getDescription());
            pstmt.setDate(4, Date.valueOf(transaction.getDate()));
            pstmt.setInt(5, transaction.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            // [Soal 2.g] Exception Handling: Penanganan SQLException
            System.err.println("Error memperbarui transaksi: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * [Soal 2.h] Implementasi CRUD - DELETE
     * [Soal 2.h] JDBC: Menggunakan PreparedStatement untuk delete data
     * 
     * @param id ID transaksi yang akan dihapus
     * @return true jika berhasil, false jika gagal
     * @throws SQLException jika terjadi error database
     */
    @Override
    public boolean deleteTransaction(int id) throws SQLException {
        // [Soal 2.h] JDBC: Statement SQL DELETE
        String sql = "DELETE FROM transactions WHERE id = ?";
        
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            // [Soal 2.g] Exception Handling: Penanganan SQLException
            System.err.println("Error menghapus transaksi: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * [Soal 2.e] Math Calculation: Menghitung total pemasukan
     * [Soal 2.h] JDBC: Menggunakan fungsi agregasi SUM
     * 
     * @return Total jumlah pemasukan
     * @throws SQLException jika terjadi error database
     */
    @Override
    public double getTotalIncome() throws SQLException {
        String sql = "SELECT SUM(amount) as total FROM transactions WHERE type = 'INCOME'";
        
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                // [Soal 2.e] Math Calculation: Mengembalikan jumlah pemasukan
                return rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            // [Soal 2.g] Exception Handling: Penanganan SQLException
            System.err.println("Error menghitung total pemasukan: " + e.getMessage());
            throw e;
        }
        
        return 0.0;
    }
    
    /**
     * [Soal 2.e] Math Calculation: Menghitung total pengeluaran
     * [Soal 2.h] JDBC: Menggunakan fungsi agregasi SUM
     * 
     * @return Total jumlah pengeluaran
     * @throws SQLException jika terjadi error database
     */
    @Override
    public double getTotalExpense() throws SQLException {
        String sql = "SELECT SUM(amount) as total FROM transactions WHERE type = 'EXPENSE'";
        
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                // [Soal 2.e] Math Calculation: Mengembalikan jumlah pengeluaran
                return rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            // [Soal 2.g] Exception Handling: Penanganan SQLException
            System.err.println("Error menghitung total pengeluaran: " + e.getMessage());
            throw e;
        }
        
        return 0.0;
    }
    
    /**
     * [Soal 2.e] Math Calculation: Menghitung saldo saat ini
     * Saldo = Total Pemasukan - Total Pengeluaran
     * 
     * @return Saldo saat ini
     * @throws SQLException jika terjadi error database
     */
    @Override
    public double getBalance() throws SQLException {
        // [Soal 2.e] Math Calculation: Saldo = Pemasukan - Pengeluaran
        double totalIncome = getTotalIncome();
        double totalExpense = getTotalExpense();
        return totalIncome - totalExpense;
    }
}
