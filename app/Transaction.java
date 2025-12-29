package app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Kelas Abstrak Transaction
 * Tujuan: Kelas induk untuk semua jenis transaksi (Pemasukan dan Pengeluaran)
 * 
 * [Soal 2.a] Class & Object: Kelas dasar untuk entitas transaksi
 * [Soal 2.b] Constructor: Memiliki constructor berparameter
 * [Soal 2.c] Inheritance: Ini adalah KELAS ABSTRAK INDUK (Parent)
 * [Soal 2.d] Polymorphism: Berisi metode abstrak yang akan di-override
 */
public abstract class Transaction {
    
    // [Soal 2.a] Atribut (Enkapsulasi)
    protected int id;
    protected String type;
    protected double amount;
    protected String description;
    protected LocalDate date;
    
    /**
     * [Soal 2.b] Constructor: Constructor Berparameter
     * 
     * @param id ID Transaksi
     * @param type Jenis transaksi (INCOME/EXPENSE)
     * @param amount Jumlah transaksi
     * @param description Deskripsi transaksi
     * @param date Tanggal transaksi
     */
    public Transaction(int id, String type, double amount, String description, LocalDate date) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }
    
    /**
     * Constructor tanpa ID (untuk transaksi baru)
     */
    public Transaction(String type, double amount, String description, LocalDate date) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }
    
    // [Soal 2.a] Metode Getter dan Setter (Enkapsulasi)
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    /**
     * [Soal 2.d] Polymorphism: Metode abstrak yang akan di-override di subclass
     * [Soal 2.f] String Manipulation: Mengembalikan string terformat
     * 
     * @return Informasi transaksi terformat
     */
    public abstract String getTransactionInfo();
    
    /**
     * [Soal 2.d] Polymorphism: Metode abstrak untuk kalkulasi saldo
     * [Soal 2.e] Math Calculation: Mengembalikan jumlah dengan tanda yang sesuai
     * 
     * @return Nilai jumlah (positif untuk pemasukan, negatif untuk pengeluaran)
     */
    public abstract double getBalanceImpact();
    
    /**
     * [Soal 2.f] Date Manipulation: Format tanggal sebagai string
     * 
     * @return String tanggal terformat
     */
    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }
    
    /**
     * [Soal 2.f] String Manipulation: Format jumlah sebagai Rupiah Indonesia
     * 
     * @param amount Jumlah yang akan diformat
     * @return String mata uang terformat
     */
    protected String formatCurrency(double amount) {
        return String.format("Rp %,.0f", amount);
    }
    
    @Override
    public String toString() {
        return String.format("ID: %d | Type: %s | Amount: %s | Description: %s | Date: %s",
                id, type, formatCurrency(amount), description, getFormattedDate());
    }
}
