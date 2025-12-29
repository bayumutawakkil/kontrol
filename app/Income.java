package app;

import java.time.LocalDate;

/**
 * Kelas Income
 * Tujuan: Merepresentasikan transaksi pemasukan
 * 
 * [Soal 2.c] Inheritance: Ini adalah SUBCLASS (Anak) yang meng-extend Transaction
 * [Soal 2.d] Polymorphism: Meng-override metode abstrak dari kelas induk
 */
public class Income extends Transaction {
    
    /**
     * [Soal 2.b] Constructor: Constructor berparameter
     * Memanggil constructor induk menggunakan super()
     * 
     * @param id ID Transaksi
     * @param amount Jumlah pemasukan
     * @param description Deskripsi pemasukan
     * @param date Tanggal pemasukan
     */
    public Income(int id, double amount, String description, LocalDate date) {
        // [Soal 2.c] Inheritance: Menggunakan super() untuk memanggil constructor induk
        super(id, "INCOME", amount, description, date);
    }
    
    /**
     * Constructor tanpa ID (untuk entri pemasukan baru)
     */
    public Income(double amount, String description, LocalDate date) {
        super("INCOME", amount, description, date);
    }
    
    /**
     * [Soal 2.d] Polymorphism: Metode OVERRIDE dari kelas induk
     * [Soal 2.f] String Manipulation: Mengembalikan informasi pemasukan terformat
     * 
     * @return Informasi transaksi pemasukan terformat
     */
    @Override
    public String getTransactionInfo() {
        return String.format("[PEMASUKAN] %s - %s pada %s", 
                formatCurrency(amount), description, getFormattedDate());
    }
    
    /**
     * [Soal 2.d] Polymorphism: Metode OVERRIDE dari kelas induk
     * [Soal 2.e] Math Calculation: Pemasukan menambah saldo (nilai positif)
     * 
     * @return Jumlah positif (pemasukan menambah saldo)
     */
    @Override
    public double getBalanceImpact() {
        // Pemasukan bernilai positif (menambah saldo)
        return amount;
    }
    
    /**
     * Metode tambahan khusus untuk Income
     * 
     * @return Label kategori
     */
    public String getCategory() {
        return "💰 PEMASUKAN";
    }
}
