package app;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface FinancialDataDAO
 * Tujuan: Mendefinisikan operasi CRUD untuk transaksi keuangan
 * 
 * [Soal 2.c] Interface: Mendefinisikan kontrak untuk operasi akses data
 * [Soal 2.h] Operasi CRUD: Metode Create, Read, Update, Delete
 */
public interface FinancialDataDAO {
    
    /**
     * [Soal 2.h] CRUD - CREATE
     * Menambahkan transaksi baru ke database
     * 
     * @param transaction Objek transaksi yang akan ditambahkan
     * @return true jika berhasil, false jika gagal
     * @throws SQLException jika terjadi error database
     */
    boolean addTransaction(Transaction transaction) throws SQLException;
    
    /**
     * [Soal 2.h] CRUD - READ (Semua)
     * [Soal 2.h] Collection Framework: Mengembalikan ArrayList berisi transaksi
     * Mengambil semua transaksi dari database
     * 
     * @return List berisi semua transaksi
     * @throws SQLException jika terjadi error database
     */
    List<Transaction> getAllTransactions() throws SQLException;
    
    /**
     * [Soal 2.h] CRUD - READ (By ID)
     * Mengambil transaksi tertentu berdasarkan ID
     * 
     * @param id ID Transaksi
     * @return Objek Transaction atau null jika tidak ditemukan
     * @throws SQLException jika terjadi error database
     */
    Transaction getTransactionById(int id) throws SQLException;
    
    /**
     * [Soal 2.h] CRUD - UPDATE
     * Memperbarui transaksi yang sudah ada
     * 
     * @param transaction Objek transaksi dengan data yang diperbarui
     * @return true jika berhasil, false jika gagal
     * @throws SQLException jika terjadi error database
     */
    boolean updateTransaction(Transaction transaction) throws SQLException;
    
    /**
     * [Soal 2.h] CRUD - DELETE
     * Menghapus transaksi dari database
     * 
     * @param id ID transaksi yang akan dihapus
     * @return true jika berhasil, false jika gagal
     * @throws SQLException jika terjadi error database
     */
    boolean deleteTransaction(int id) throws SQLException;
    
    /**
     * [Soal 2.e] Math Calculation: Menghitung total pemasukan
     * 
     * @return Total jumlah pemasukan
     * @throws SQLException jika terjadi error database
     */
    double getTotalIncome() throws SQLException;
    
    /**
     * [Soal 2.e] Math Calculation: Menghitung total pengeluaran
     * 
     * @return Total jumlah pengeluaran
     * @throws SQLException jika terjadi error database
     */
    double getTotalExpense() throws SQLException;
    
    /**
     * [Soal 2.e] Math Calculation: Menghitung saldo saat ini
     * Saldo = Total Pemasukan - Total Pengeluaran
     * 
     * @return Saldo saat ini
     * @throws SQLException jika terjadi error database
     */
    double getBalance() throws SQLException;
}
