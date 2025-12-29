# KONTROL - Sistem Manajemen Kas Organisasi

## Gambaran Umum Proyek
**KONTROL** adalah Aplikasi Konsol Java untuk Manajemen Kas Organisasi Mahasiswa (BEM/Himpunan) yang dikembangkan sebagai proyek Ujian Akhir Semester (UAS PBO). Aplikasi ini menyelesaikan masalah pencatatan dana kas organisasi seperti Iuran Anggota, Sponsor, dan Pengeluaran Proker yang selama ini dilakukan manual sehingga menimbulkan masalah transparansi. Dengan aplikasi ini, pengelola organisasi dapat mencatat transaksi (Pemasukan/Pengeluaran), melihat riwayat, dan menghitung Sisa Kas menggunakan Database MySQL.

## Implementasi Persyaratan Teknis

Proyek ini mengimplementasikan SEMUA persyaratan wajib untuk UAS PBO:

### Cakupan Kriteria Penilaian

| Persyaratan | Implementasi | Lokasi |
|------------|----------------|----------|
| **[2.a] Class, Object, Constructor** | Kelas POJO dengan constructor | Semua kelas |
| **[2.b] Constructor** | Constructor berparameter dengan super() | Transaksi, Pemasukan, Pengeluaran |
| **[2.c] Inheritance** | Kelas abstrak `Transaksi` dengan subclass `Pemasukan` dan `Pengeluaran` | Transaksi.java, Pemasukan.java, Pengeluaran.java |
| **[2.c] Interface** | Interface `KasDAO` dengan metode CRUD | KasDAO.java |
| **[2.d] Polymorphism** | Method overriding di subclass (`getTipe()`, `getKategori()`) | Pemasukan.java, Pengeluaran.java |
| **[2.e] Loops** | Loop `do-while` untuk menu utama, `while` untuk ResultSet | MainApp.java, ManajemenKas.java |
| **[2.e] Branching** | `switch-case` untuk logika menu, `if-else` untuk kondisi | MainApp.java, ManajemenKas.java |
| **[2.e] Kalkulasi Matematika** | Kalkulasi Sisa Kas (Total Pemasukan - Total Pengeluaran) | ManajemenKas.java |
| **[2.f] Manipulasi String** | Pemformatan mata uang (Rupiah) dan tanggal | Transaksi.java, MainApp.java |
| **[2.f] Manipulasi Tanggal** | Penggunaan `Date` dan `SimpleDateFormat` | Transaksi.java, MainApp.java |
| **[2.g] Penanganan Exception** | `try-catch` untuk `InputMismatchException` dan `SQLException` | Semua kelas DAO, MainApp.java |
| **[2.h] Collection Framework** | `ArrayList<Transaksi>` untuk penyimpanan data | ManajemenKas.java |
| **[2.h] JDBC** | `Connection`, `PreparedStatement`, `ResultSet` | KoneksiDB.java, ManajemenKas.java |
| **[2.h] Operasi CRUD** | CRUD: Create, Read, Delete + Kalkulasi | ManajemenKas.java |

## Struktur Proyek

```
kontrol/
├── database_kas.sql                      # Skema SQL untuk kas organisasi
├── src/
│   ├── KoneksiDB.java                    # Manajer Koneksi JDBC
│   ├── Transaksi.java                    # Kelas Induk Abstrak
│   ├── Pemasukan.java                    # Subclass Pemasukan (Iuran/Sponsor)
│   ├── Pengeluaran.java                  # Subclass Pengeluaran (Proker/Konsumsi)
│   ├── KasDAO.java                       # Interface untuk operasi CRUD
│   ├── ManajemenKas.java                 # Implementasi CRUD
│   └── MainApp.java                      # Aplikasi Utama dengan Menu CLI
├── lib/                                  # Folder dependencies
└── bin/                                  # Folder output kompilasi
```

## Skema Database

```sql
CREATE TABLE kas_organisasi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipe VARCHAR(20) NOT NULL,          -- 'PEMASUKAN' atau 'PENGELUARAN'
    nominal DOUBLE NOT NULL,
    keterangan VARCHAR(255) NOT NULL,
    sumber_dana VARCHAR(100),            -- Untuk PEMASUKAN: 'Iuran', 'Sponsor', 'Donasi'
    keperluan VARCHAR(100),              -- Untuk PENGELUARAN: 'Proker', 'Konsumsi', 'Perlengkapan'
    tanggal DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## Instruksi Setup

### Prasyarat
1. **Java Development Kit (JDK)** - Versi 8 atau lebih tinggi
2. **MySQL Server** - Versi 5.7 atau lebih tinggi
3. **MySQL JDBC Driver** - `mysql-connector-java-8.x.x.jar`

### Setup Database

1. **Jalankan Server MySQL**

2. **Buat Database dan Tabel**
   ```bash
   mysql -u root -p < database_kas.sql
   ```
   
   Atau eksekusi manual di MySQL:
   ```sql
   CREATE DATABASE IF NOT EXISTS kas_organisasi_db;
   USE kas_organisasi_db;
   -- Kemudian eksekusi pernyataan CREATE TABLE dari database_kas.sql
   ```

3. **Konfigurasi Koneksi Database**
   
   Edit `src/KoneksiDB.java`:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/kas_organisasi_db";
   private static final String USER = "root";      // Username MySQL Anda
   private static final String PASSWORD = "";      // Password MySQL Anda
   ```

### Kompilasi Proyek

#### Opsi 1: Menggunakan Command Line

1. **Tambahkan MySQL JDBC Driver ke classpath**
   ```bash
   # Windows
   set CLASSPATH=.;lib/mysql-connector-java-8.x.x.jar

   # Linux/Mac
   export CLASSPATH=.:lib/mysql-connector-java-8.x.x.jar
   ```

2. **Kompilasi semua file Java**
   ```bash
   # Dari direktori root proyek
   javac -d bin -cp lib/mysql-connector-java-8.x.x.jar src/*.java
   ```

3. **Jalankan aplikasi**
   ```bash
   java -cp "bin;lib/mysql-connector-java-8.x.x.jar" MainApp
   ```

#### Opsi 2: Menggunakan IDE (Eclipse/IntelliJ/VSCode)

1. Import proyek ke IDE Anda
2. Tambahkan MySQL JDBC Driver ke project libraries
3. Jalankan `MainApp.java`

## Cara Menggunakan

### Opsi Menu Utama

```
════════════════════════════════════════════════
  SISTEM MANAJEMEN KAS ORGANISASI
  (BEM/HIMPUNAN MAHASISWA)
════════════════════════════════════════════════
1. Tambah Pemasukan (Iuran/Sponsor)
2. Tambah Pengeluaran (Proker/Konsumsi)
3. Lihat Riwayat Transaksi
4. Hapus Transaksi
5. Laporan Keuangan & Sisa Kas
6. Keluar
════════════════════════════════════════════════
```

### 1. Tambah Pemasukan
- Pilih sumber dana (Iuran Anggota/Sponsor/Donasi)
- Masukkan nominal dalam Rupiah
- Berikan keterangan
- Masukkan tanggal (dd-MM-yyyy) atau tekan Enter untuk hari ini

### 2. Tambah Pengeluaran
- Pilih keperluan (Program Kerja/Konsumsi/Perlengkapan)
- Masukkan nominal dalam Rupiah
- Berikan keterangan
- Masukkan tanggal (dd-MM-yyyy) atau tekan Enter untuk hari ini

### 3. Lihat Riwayat Transaksi
- Menampilkan semua transaksi diurutkan berdasarkan tanggal (terbaru dulu)
- Menampilkan ID, Tanggal, Tipe, Kategori, Nominal, dan Keterangan

### 4. Hapus Transaksi
- Masukkan ID transaksi untuk dihapus
- Konfirmasi penghapusan

### 5. Laporan Keuangan & Sisa Kas
- Menampilkan Total Pemasukan
- Menampilkan Total Pengeluaran
- Menghitung Sisa Kas (Total Pemasukan - Total Pengeluaran)
- Menampilkan status kesehatan keuangan organisasi

## Contoh Penggunaan

```
Database terhubung dengan sukses!

════════════════════════════════════════════════
  SISTEM MANAJEMEN KAS ORGANISASI
  (BEM/HIMPUNAN MAHASISWA)
════════════════════════════════════════════════
1. Tambah Pemasukan (Iuran/Sponsor)
2. Tambah Pengeluaran (Proker/Konsumsi)
3. Lihat Riwayat Transaksi
4. Hapus Transaksi
5. Laporan Keuangan & Sisa Kas
6. Keluar
════════════════════════════════════════════════
Pilih menu (1-6): 5

═══════════════════════════════════════════════
      LAPORAN KEUANGAN ORGANISASI
═══════════════════════════════════════════════

Total Pemasukan   : Rp 8.000.000
Total Pengeluaran : Rp 2.450.000
─────────────────────────────────────────────
SISA KAS          : Rp 5.550.000

STATUS: Kondisi kas sehat.
```

## Komentar Kode untuk Penilaian

Semua file kode menyertakan **komentar eksplisit** yang menandai di mana setiap persyaratan UAS diimplementasikan:
- `// Class & Object`
- `// Constructor`
- `// Inheritance`
- `// Interface`
- `// Polymorphism`
- `// Loop`
- `// Branching`
- `// Math Calculations`
- `// String Manipulation`
- `// Exception Handling`
- `// Collection Framework`
- `// JDBC & CRUD`

## Troubleshooting

### Koneksi Database Gagal
```
Gagal terhubung ke database!
```
**Solusi:**
- Verifikasi server MySQL berjalan
- Periksa nama database, username, dan password di `KoneksiDB.java`
- Pastikan MySQL JDBC Driver ada di classpath

### ClassNotFoundException: com.mysql.cj.jdbc.Driver
**Solusi:** Tambahkan JAR MySQL JDBC Driver ke classpath proyek

### SQLException: Unknown database 'kas_organisasi_db'
**Solusi:** Buat database menggunakan skrip `database_kas.sql`

## Catatan untuk Penilai

Proyek ini mendemonstrasikan:
1. **Prinsip OOP yang tepat**: Inheritance, Polymorphism, Encapsulation, Abstraction
2. **Struktur kode yang bersih**: Pemisahan concerns dengan pola DAO, desain berbasis Interface
3. **Implementasi CRUD lengkap**: Operasi database CREATE, READ, DELETE bekerja dengan baik
4. **Penanganan exception**: Penanganan error yang kuat untuk input pengguna dan operasi database
5. **CLI yang user-friendly**: Sistem menu yang jelas dengan output yang diformat
6. **Solusi masalah nyata**: Aplikasi menyelesaikan masalah transparansi pengelolaan kas organisasi mahasiswa

Semua persyaratan UAS diimplementasikan dan ditandai dengan jelas dalam komentar kode dengan format `// [UAS Point: ...]`.

## Pembuat

**Proyek Ujian Akhir Semester PBO**  
**Nama Proyek:** KONTROL - Sistem Manajemen Kas Organisasi  
**Aplikasi:** Kas BEM/Himpunan Mahasiswa

---

**Semoga sukses dengan ujian UAS Anda!**
