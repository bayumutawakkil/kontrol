import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

// Class Utama
public class MainApp {
    
    private static final Scanner scanner = new Scanner(System.in);
    private static final KasDAO kasDAO = new ManajemenKas();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    
    public static void main(String[] args) {
        // Test koneksi database
        System.out.println("========================================");
        System.out.println("  SISTEM MANAJEMEN KAS ORGANISASI");
        System.out.println("  (BEM/HIMPUNAN MAHASISWA)");
        System.out.println("========================================");
        
        if (!KoneksiDB.testConnection()) {
            System.out.println("\n[ERROR] Koneksi database gagal!");
            System.out.println("Pastikan MySQL Server berjalan dan database sudah dibuat.");
            return;
        }
        
        System.out.println("[OK] Koneksi database berhasil!\n");
        
        int pilihan = 0;
        
        // Loop - do-while untuk menu
        do {
            tampilkanMenu();
            
            try {
                System.out.print("Pilih menu (1-6): ");
                pilihan = scanner.nextInt();
                scanner.nextLine(); // Bersihkan buffer
                
                System.out.println();
                
                // Percabangan - switch-case
                switch (pilihan) {
                    case 1:
                        tambahPemasukan();
                        break;
                    case 2:
                        tambahPengeluaran();
                        break;
                    case 3:
                        lihatRiwayatTransaksi();
                        break;
                    case 4:
                        hapusTransaksi();
                        break;
                    case 5:
                        lihatLaporanKeuangan();
                        break;
                    case 6:
                        System.out.println("Terima kasih! Aplikasi ditutup.");
                        break;
                    default:
                        System.out.println("[!] Pilihan tidak valid. Silakan pilih 1-6.");
                }
                
            } catch (InputMismatchException e) {
                // Penanganan Exception - Validasi input
                System.out.println("\n[ERROR] Input harus berupa angka!");
                scanner.nextLine(); // Bersihkan input yang tidak valid
            } catch (Exception e) {
                System.out.println("\n[ERROR] " + e.getMessage());
            }
            
            System.out.println();
            
        } while (pilihan != 6);
        
        scanner.close();
    }
    
    private static void tampilkanMenu() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║           MENU UTAMA                   ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. Tambah Pemasukan (Iuran/Sponsor)    ║");
        System.out.println("║ 2. Tambah Pengeluaran (Proker/Konsumsi)║");
        System.out.println("║ 3. Lihat Riwayat Transaksi             ║");
        System.out.println("║ 4. Hapus Transaksi                     ║");
        System.out.println("║ 5. Laporan Keuangan & Sisa Kas         ║");
        System.out.println("║ 6. Keluar                              ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
    
    private static void tambahPemasukan() {
        try {
            System.out.println("=== TAMBAH PEMASUKAN ===");
            
            System.out.print("Sumber Dana (Iuran Anggota/Sponsor/Donasi): ");
            String sumberDana = scanner.nextLine();
            
            System.out.print("Nominal (Rp): ");
            double nominal = scanner.nextDouble();
            scanner.nextLine();
            
            System.out.print("Keterangan: ");
            String keterangan = scanner.nextLine();
            
            System.out.print("Tanggal (dd-MM-yyyy) [Enter = Hari ini]: ");
            String tanggalStr = scanner.nextLine();
            
            // Manipulasi Tanggal
            Date tanggal = tanggalStr.isEmpty() ? new Date() : dateFormat.parse(tanggalStr);
            
            // Pembuatan Object - Polymorphism
            Transaksi pemasukan = new Pemasukan(0, nominal, keterangan, tanggal, sumberDana);
            
            // CRUD - Create
            kasDAO.tambahTransaksi(pemasukan);
            
            System.out.println("\n[✓] Pemasukan berhasil ditambahkan!");
            
        } catch (InputMismatchException e) {
            System.out.println("[ERROR] Format nominal salah!");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }
    
    private static void tambahPengeluaran() {
        try {
            System.out.println("=== TAMBAH PENGELUARAN ===");
            
            System.out.print("Keperluan (Program Kerja/Konsumsi/Perlengkapan): ");
            String keperluan = scanner.nextLine();
            
            System.out.print("Nominal (Rp): ");
            double nominal = scanner.nextDouble();
            scanner.nextLine();
            
            System.out.print("Keterangan: ");
            String keterangan = scanner.nextLine();
            
            System.out.print("Tanggal (dd-MM-yyyy) [Enter = Hari ini]: ");
            String tanggalStr = scanner.nextLine();
            
            Date tanggal = tanggalStr.isEmpty() ? new Date() : dateFormat.parse(tanggalStr);
            
            // Pembuatan Object - Polymorphism
            Transaksi pengeluaran = new Pengeluaran(0, nominal, keterangan, tanggal, keperluan);
            
            kasDAO.tambahTransaksi(pengeluaran);
            
            System.out.println("\n[✓] Pengeluaran berhasil ditambahkan!");
            
        } catch (InputMismatchException e) {
            System.out.println("[ERROR] Format nominal salah!");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }
    
    private static void lihatRiwayatTransaksi() {
        try {
            System.out.println("=== RIWAYAT TRANSAKSI KAS ORGANISASI ===\n");
            
            // CRUD - Read
            // Collection Framework - ArrayList
            List<Transaksi> daftarTransaksi = kasDAO.getAllTransaksi();
            
            if (daftarTransaksi.isEmpty()) {
                System.out.println("[i] Belum ada transaksi.");
                return;
            }
            
            System.out.println("┌─────┬────────────┬─────────────┬──────────────────────┬──────────────────┬────────────────────┐");
            System.out.println("│ ID  │   Tanggal  │    Tipe     │      Kategori        │      Nominal     │    Keterangan      │");
            System.out.println("├─────┼────────────┼─────────────┼──────────────────────┼──────────────────┼────────────────────┤");
            
            // Loop - for-each
            for (Transaksi t : daftarTransaksi) {
                System.out.printf("│ %-3d │ %-10s │ %-11s │ %-20s │ %-16s │ %-18s │%n",
                    t.getId(),
                    t.getTanggalFormatted(),
                    t.getTipe(),
                    t.getKategori(),
                    t.getNominalFormatted(),
                    t.getKeterangan().length() > 18 ? t.getKeterangan().substring(0, 15) + "..." : t.getKeterangan()
                );
            }
            
            System.out.println("└─────┴────────────┴─────────────┴──────────────────────┴──────────────────┴────────────────────┘");
            System.out.println("\nTotal Transaksi: " + daftarTransaksi.size());
            
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }
    
    private static void hapusTransaksi() {
        try {
            System.out.println("=== HAPUS TRANSAKSI ===");
            System.out.print("Masukkan ID transaksi yang akan dihapus: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Yakin ingin menghapus transaksi ID " + id + "? (y/n): ");
            String konfirmasi = scanner.nextLine();
            
            // Percabangan - if-else
            if (konfirmasi.equalsIgnoreCase("y")) {
                // CRUD - Delete
                kasDAO.hapusTransaksi(id);
                System.out.println("\n[✓] Transaksi berhasil dihapus!");
            } else {
                System.out.println("\n[i] Penghapusan dibatalkan.");
            }
            
        } catch (InputMismatchException e) {
            System.out.println("[ERROR] ID harus berupa angka!");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }
    
    private static void lihatLaporanKeuangan() {
        try {
            System.out.println("╔═══════════════════════════════════════════╗");
            System.out.println("║      LAPORAN KEUANGAN ORGANISASI          ║");
            System.out.println("╚═══════════════════════════════════════════╝");
            
            // Perhitungan Matematika
            double totalPemasukan = kasDAO.getTotalPemasukan();
            double totalPengeluaran = kasDAO.getTotalPengeluaran();
            double sisaKas = kasDAO.getSisaKas();
            
            // Manipulasi String - Format mata uang
            // Diperbaiki: Menggunakan Locale constructor (kompatibel dengan Java 8+)
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            
            System.out.println("\n📈 Total Pemasukan   : " + formatRupiah.format(totalPemasukan));
            System.out.println("📉 Total Pengeluaran : " + formatRupiah.format(totalPengeluaran));
            System.out.println("─────────────────────────────────────────────");
            System.out.println("💰 SISA KAS          : " + formatRupiah.format(sisaKas));
            
            // Percabangan - Logika kondisional
            if (sisaKas < 0) {
                System.out.println("\n⚠️  STATUS: DEFISIT! Kas dalam kondisi minus.");
            } else if (sisaKas < 1000000) {
                System.out.println("\n⚠️  STATUS: Kas menipis, perlu penghematan.");
            } else {
                System.out.println("\n✅ STATUS: Kondisi kas sehat.");
            }
            
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }
}
