package app;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Kelas Main
 * Tujuan: Aplikasi utama dengan Menu CLI
 * 
 * [Soal 2.a] Class & Object: Kelas aplikasi utama
 * [Soal 2.e] Loop & Branching: Menggunakan loop do-while dan switch-case
 */
public class Main {
    
    // [Soal 2.a] Object: Instance DAO
    private static FinancialDataDAO dao = new TransactionDAO();
    private static Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    /**
     * Metode Main - Titik masuk aplikasi
     * 
     * @param args Argumen command line
     */
    public static void main(String[] args) {
        // Tes koneksi database terlebih dahulu
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║          KONTROL - Finance Management          ║");
        System.out.println("║        Personal Finance Tracker System         ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.println();
        
        // Cek koneksi database
        if (!DatabaseHelper.testConnection()) {
            System.err.println("❌ Gagal terhubung ke database!");
            System.err.println("Silakan cek konfigurasi MySQL Anda di DatabaseHelper.java");
            return;
        }
        
        System.out.println("✅ Database terhubung dengan sukses!");
        System.out.println();
        
        // [Soal 2.e] Loop: loop do-while untuk menu utama
        showMainMenu();
    }
    
    /**
     * [Soal 2.e] Loop & Branching: Menu utama dengan loop do-while dan switch-case
     * Menampilkan menu utama dan menangani pilihan pengguna
     */
    private static void showMainMenu() {
        int choice;
        
        // [Soal 2.e] Loop: loop do-while untuk tampilan menu berkelanjutan
        do {
            printMenuHeader();
            System.out.println("1. ➕ Tambah Transaksi Baru");
            System.out.println("2. 📋 Lihat Semua Transaksi");
            System.out.println("3. ✏️  Edit Transaksi");
            System.out.println("4. ❌ Hapus Transaksi");
            System.out.println("5. 💰 Lihat Ringkasan Keuangan");
            System.out.println("6. 🚪 Keluar");
            System.out.println("════════════════════════════════════════════════");
            System.out.print("Pilih menu (1-6): ");
            
            try {
                // [Soal 2.g] Exception Handling: Menangani InputMismatchException
                choice = scanner.nextInt();
                scanner.nextLine(); // Bersihkan buffer
                
                System.out.println();
                
                // [Soal 2.e] Branching: statement switch-case
                switch (choice) {
                    case 1:
                        addNewTransaction();
                        break;
                    case 2:
                        viewAllTransactions();
                        break;
                    case 3:
                        editTransaction();
                        break;
                    case 4:
                        deleteTransaction();
                        break;
                    case 5:
                        viewFinancialSummary();
                        break;
                    case 6:
                        System.out.println("════════════════════════════════════════════════");
                        System.out.println("   Terima kasih telah menggunakan KONTROL! 👋");
                        System.out.println("════════════════════════════════════════════════");
                        scanner.close();
                        return;
                    default:
                        System.out.println("❌ Pilihan tidak valid! Silakan pilih 1-6.");
                        System.out.println();
                }
                
            } catch (InputMismatchException e) {
                // [Soal 2.g] Exception Handling: Menangkap InputMismatchException
                System.out.println("❌ Error: Input harus berupa angka!");
                scanner.nextLine(); // Bersihkan input yang tidak valid
                choice = 0; // Lanjutkan loop
                System.out.println();
            }
            
        } while (true);
    }
    
    /**
     * Cetak header menu
     */
    private static void printMenuHeader() {
        System.out.println("════════════════════════════════════════════════");
        System.out.println("              📊 MENU UTAMA 📊");
        System.out.println("════════════════════════════════════════════════");
    }
    
    /**
     * [Soal 2.h] CRUD - CREATE: Menambahkan transaksi baru
     * [Soal 2.d] Polymorphism: Membuat objek Income atau Expense
     */
    private static void addNewTransaction() {
        System.out.println("┌────────────────────────────────────────────────┐");
        System.out.println("│         ➕ TAMBAH TRANSAKSI BARU               │");
        System.out.println("└────────────────────────────────────────────────┘");
        
        try {
            // Get transaction type
            System.out.print("Jenis transaksi (1=Pemasukan, 2=Pengeluaran): ");
            int typeChoice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer
            
            String type;
            if (typeChoice == 1) {
                type = "INCOME";
            } else if (typeChoice == 2) {
                type = "EXPENSE";
            } else {
                System.out.println("❌ Jenis transaksi tidak valid!");
                return;
            }
            
            // Get amount
            System.out.print("Jumlah (Rp): ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Clear buffer
            
            if (amount <= 0) {
                System.out.println("❌ Jumlah harus lebih dari 0!");
                return;
            }
            
            // Get description
            System.out.print("Deskripsi: ");
            String description = scanner.nextLine();
            
            // Dapatkan tanggal
            System.out.print("Tanggal (dd-MM-yyyy) atau tekan Enter untuk hari ini: ");
            String dateInput = scanner.nextLine();
            LocalDate date;
            
            if (dateInput.trim().isEmpty()) {
                // [Soal 2.f] Date Manipulation: Gunakan tanggal saat ini
                date = LocalDate.now();
            } else {
                try {
                    // [Soal 2.f] Date Manipulation: Parse tanggal dari string
                    date = LocalDate.parse(dateInput, DATE_FORMATTER);
                } catch (DateTimeParseException e) {
                    // [Soal 2.g] Exception Handling: Menangani exception parse tanggal
                    System.out.println("❌ Format tanggal tidak valid! Menggunakan tanggal hari ini.");
                    date = LocalDate.now();
                }
            }
            
            // [Soal 2.d] Polymorphism: Membuat objek subclass yang sesuai
            Transaction transaction;
            if ("INCOME".equals(type)) {
                transaction = new Income(amount, description, date);
            } else {
                transaction = new Expense(amount, description, date);
            }
            
            // [Soal 2.h] CRUD - CREATE: Menambahkan ke database
            if (dao.addTransaction(transaction)) {
                System.out.println("✅ Transaksi berhasil ditambahkan!");
                // [Soal 2.d] Polymorphism: Memanggil metode yang di-override
                System.out.println("   " + transaction.getTransactionInfo());
            } else {
                System.out.println("❌ Gagal menambahkan transaksi!");
            }
            
        } catch (InputMismatchException e) {
            // [Soal 2.g] Exception Handling: Handle input mismatch
            System.out.println("❌ Error: Input tidak valid!");
            scanner.nextLine(); // Clear buffer
        } catch (SQLException e) {
            // [Soal 2.g] Exception Handling: Handle SQL exception
            System.out.println("❌ Database Error: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * [Soal 2.h] CRUD - READ: Melihat semua transaksi
     * [Soal 2.h] Collection Framework: Menampilkan data ArrayList
     */
    private static void viewAllTransactions() {
        System.out.println("┌────────────────────────────────────────────────┐");
        System.out.println("│         📋 DAFTAR SEMUA TRANSAKSI              │");
        System.out.println("└────────────────────────────────────────────────┘");
        
        try {
            // [Soal 2.h] CRUD - READ: Dapatkan semua transaksi
            // [Soal 2.h] Collection Framework: Ambil ArrayList
            List<Transaction> transactions = dao.getAllTransactions();
            
            if (transactions.isEmpty()) {
                System.out.println("📭 Belum ada transaksi.");
            } else {
                System.out.println("Total: " + transactions.size() + " transaksi\n");
                
                // [Soal 2.e] Loop: Iterasi melalui collection
                for (Transaction t : transactions) {
                    System.out.println("─────────────────────────────────────────────");
                    System.out.println("ID       : " + t.getId());
                    System.out.println("Jenis    : " + t.getType());
                    // [Soal 2.d] Polymorphism: Memanggil metode yang di-override
                    System.out.println("Info     : " + t.getTransactionInfo());
                    // [Soal 2.f] String Manipulation: Format mata uang
                    System.out.println("Jumlah   : " + formatCurrency(t.getAmount()));
                    // [Soal 2.f] Date Manipulation: Tampilkan tanggal terformat
                    System.out.println("Tanggal  : " + t.getFormattedDate());
                }
                System.out.println("─────────────────────────────────────────────");
            }
            
        } catch (SQLException e) {
            // [Soal 2.g] Exception Handling: Menangani SQL exception
            System.out.println("❌ Database Error: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * [Soal 2.h] CRUD - UPDATE: Mengedit transaksi yang sudah ada
     */
    private static void editTransaction() {
        System.out.println("┌────────────────────────────────────────────────┐");
        System.out.println("│         ✏️  EDIT TRANSAKSI                     │");
        System.out.println("└────────────────────────────────────────────────┘");
        
        try {
            System.out.print("Masukkan ID transaksi yang akan diedit: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Bersihkan buffer
            
            // [Soal 2.h] CRUD - READ: Dapatkan transaksi berdasarkan ID
            Transaction transaction = dao.getTransactionById(id);
            
            if (transaction == null) {
                System.out.println("❌ Transaksi dengan ID " + id + " tidak ditemukan!");
                return;
            }
            
            System.out.println("\n📌 Transaksi saat ini:");
            System.out.println("   " + transaction.toString());
            System.out.println();
            
            // Dapatkan nilai baru
            System.out.print("Jenis baru (1=Pemasukan, 2=Pengeluaran) [" + transaction.getType() + "]: ");
            String typeInput = scanner.nextLine();
            String newType = transaction.getType();
            
            if (!typeInput.trim().isEmpty()) {
                int typeChoice = Integer.parseInt(typeInput);
                newType = (typeChoice == 1) ? "INCOME" : "EXPENSE";
            }
            
            System.out.print("Jumlah baru (Rp) [" + transaction.getAmount() + "]: ");
            String amountInput = scanner.nextLine();
            double newAmount = transaction.getAmount();
            
            if (!amountInput.trim().isEmpty()) {
                newAmount = Double.parseDouble(amountInput);
            }
            
            System.out.print("Deskripsi baru [" + transaction.getDescription() + "]: ");
            String newDescription = scanner.nextLine();
            if (newDescription.trim().isEmpty()) {
                newDescription = transaction.getDescription();
            }
            
            System.out.print("Tanggal baru (dd-MM-yyyy) [" + transaction.getFormattedDate() + "]: ");
            String dateInput = scanner.nextLine();
            LocalDate newDate = transaction.getDate();
            
            if (!dateInput.trim().isEmpty()) {
                try {
                    newDate = LocalDate.parse(dateInput, DATE_FORMATTER);
                } catch (DateTimeParseException e) {
                    System.out.println("⚠️  Format tanggal tidak valid! Menggunakan tanggal lama.");
                }
            }
            
            // [Soal 2.d] Polymorphism: Membuat objek transaksi yang diperbarui
            Transaction updatedTransaction;
            if ("INCOME".equals(newType)) {
                updatedTransaction = new Income(id, newAmount, newDescription, newDate);
            } else {
                updatedTransaction = new Expense(id, newAmount, newDescription, newDate);
            }
            
            // [Soal 2.h] CRUD - UPDATE: Update di database
            if (dao.updateTransaction(updatedTransaction)) {
                System.out.println("✅ Transaksi berhasil diupdate!");
                System.out.println("   " + updatedTransaction.getTransactionInfo());
            } else {
                System.out.println("❌ Gagal mengupdate transaksi!");
            }
            
        } catch (InputMismatchException | NumberFormatException e) {
            // [Soal 2.g] Exception Handling: Menangani input exception
            System.out.println("❌ Error: Input tidak valid!");
            scanner.nextLine(); // Bersihkan buffer
        } catch (SQLException e) {
            // [Soal 2.g] Exception Handling: Menangani SQL exception
            System.out.println("❌ Database Error: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * [Soal 2.h] CRUD - DELETE: Menghapus transaksi
     */
    private static void deleteTransaction() {
        System.out.println("┌────────────────────────────────────────────────┐");
        System.out.println("│         ❌ HAPUS TRANSAKSI                     │");
        System.out.println("└────────────────────────────────────────────────┘");
        
        try {
            System.out.print("Masukkan ID transaksi yang akan dihapus: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Bersihkan buffer
            
            // [Soal 2.h] CRUD - READ: Cek apakah transaksi ada
            Transaction transaction = dao.getTransactionById(id);
            
            if (transaction == null) {
                System.out.println("❌ Transaksi dengan ID " + id + " tidak ditemukan!");
                return;
            }
            
            System.out.println("\n📌 Transaksi yang akan dihapus:");
            System.out.println("   " + transaction.toString());
            
            System.out.print("\n⚠️  Apakah Anda yakin ingin menghapus? (y/n): ");
            String confirm = scanner.nextLine();
            
            // [Soal 2.e] Branching: statement if-else
            if (confirm.equalsIgnoreCase("y")) {
                // [Soal 2.h] CRUD - DELETE: Hapus dari database
                if (dao.deleteTransaction(id)) {
                    System.out.println("✅ Transaksi berhasil dihapus!");
                } else {
                    System.out.println("❌ Gagal menghapus transaksi!");
                }
            } else {
                System.out.println("❌ Penghapusan dibatalkan.");
            }
            
        } catch (InputMismatchException e) {
            // [Soal 2.g] Exception Handling: Menangani input mismatch
            System.out.println("❌ Error: Input tidak valid!");
            scanner.nextLine(); // Bersihkan buffer
        } catch (SQLException e) {
            // [Soal 2.g] Exception Handling: Menangani SQL exception
            System.out.println("❌ Database Error: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * [Soal 2.e] Math Calculation: Menampilkan ringkasan keuangan
     * Menghitung dan menampilkan total pemasukan, pengeluaran, dan saldo
     */
    private static void viewFinancialSummary() {
        System.out.println("┌────────────────────────────────────────────────┐");
        System.out.println("│         💰 RINGKASAN KEUANGAN                  │");
        System.out.println("└────────────────────────────────────────────────┘");
        
        try {
            // [Soal 2.e] Math Calculation: Dapatkan total pemasukan
            double totalIncome = dao.getTotalIncome();
            
            // [Soal 2.e] Math Calculation: Dapatkan total pengeluaran
            double totalExpense = dao.getTotalExpense();
            
            // [Soal 2.e] Math Calculation: Hitung saldo
            double balance = dao.getBalance();
            
            System.out.println();
            System.out.println("💵 Total Pemasukan  : " + formatCurrency(totalIncome));
            System.out.println("💸 Total Pengeluaran: " + formatCurrency(totalExpense));
            System.out.println("─────────────────────────────────────────────");
            
            // [Soal 2.e] Branching: Tampilkan saldo dengan indikator warna
            if (balance >= 0) {
                System.out.println("✅ Saldo Saat Ini   : " + formatCurrency(balance));
                System.out.println("   Status: Keuangan SEHAT 💚");
            } else {
                System.out.println("⚠️  Saldo Saat Ini   : " + formatCurrency(balance));
                System.out.println("   Status: Keuangan DEFISIT 🔴");
            }
            
            System.out.println("─────────────────────────────────────────────");
            
            // [Soal 2.e] Math Calculation: Hitung tingkat tabungan
            if (totalIncome > 0) {
                double savingsRate = ((totalIncome - totalExpense) / totalIncome) * 100;
                System.out.println("📊 Tingkat Tabungan : " + String.format("%.2f%%", savingsRate));
            }
            
        } catch (SQLException e) {
            // [Soal 2.g] Exception Handling: Menangani SQL exception
            System.out.println("❌ Database Error: " + e.getMessage());
        }
        
        System.out.println();
    }
    
    /**
     * [Soal 2.f] String Manipulation: Format jumlah sebagai Rupiah Indonesia
     * 
     * @param amount Jumlah yang akan diformat
     * @return String mata uang terformat
     */
    private static String formatCurrency(double amount) {
        return String.format("Rp %,.0f", amount);
    }
}
