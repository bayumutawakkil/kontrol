package app;

import java.time.LocalDate;

/**
 * Kelas Expense
 * Tujuan: Merepresentasikan transaksi pengeluaran
 * 
 * [Soal 2.c] Inheritance: Ini adalah SUBCLASS (Anak) yang meng-extend Transaction
 * [Soal 2.d] Polymorphism: Meng-override metode abstrak dari kelas induk
 */
public class Expense extends Transaction {
    
    /**
     * [Soal 2.b] Constructor: Constructor berparameter
     * Memanggil constructor induk menggunakan super()
     * 
     * @param id ID Transaksi
     * @param amount Jumlah pengeluaran
     * @param description Deskripsi pengeluaran
     * @param date Tanggal pengeluaran
     */
    public Expense(int id, double amount, String description, LocalDate date) {
        // [Soal 2.c] Inheritance: Menggunakan super() untuk memanggil constructor induk
        super(id, "EXPENSE", amount, description, date);
    }
    
    /**
     * Constructor tanpa ID (untuk entri pengeluaran baru)
     */
    public Expense(double amount, String description, LocalDate date) {
        super("EXPENSE", amount, description, date);
    }
    
    /**
     * [Soal 2.d] Polymorphism: Metode OVERRIDE dari kelas induk
     * [Soal 2.f] String Manipulation: Mengembalikan informasi pengeluaran terformat
     * 
     * @return Informasi transaksi pengeluaran terformat
     */
    @Override
    public String getTransactionInfo() {
        return String.format("[PENGELUARAN] %s - %s pada %s", 
                formatCurrency(amount), description, getFormattedDate());
    }
    
    /**
     * [Soal 2.d] Polymorphism: Metode OVERRIDE dari kelas induk
     * [Soal 2.e] Math Calculation: Pengeluaran mengurangi saldo (nilai negatif)
     * 
     * @return Jumlah negatif (pengeluaran mengurangi saldo)
     */
    @Override
    public double getBalanceImpact() {
        // Pengeluaran bernilai negatif (mengurangi saldo)
        return -amount;
    }
    
    /**
     * Metode tambahan khusus untuk Expense
     * 
     * @return Label kategori
     */
    public String getCategory() {
        return "💸 PENGELUARAN";
    }
}
