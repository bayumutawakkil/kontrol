# Aplikasi Kas Organisasi - Panduan Setup dan Instalasi

## Panduan Setup Cepat

### Langkah 1: Download MySQL JDBC Driver

1. Download MySQL Connector/J dari: https://dev.mysql.com/downloads/connector/j/
2. Ekstrak file JAR (misalnya, `mysql-connector-java-8.0.33.jar`)
3. Letakkan di folder `lib/` proyek ini

### Langkah 2: Setup MySQL Database

**Opsi A: Menggunakan Command Line**
```bash
mysql -u root -p
```

Kemudian eksekusi:
```sql
CREATE DATABASE IF NOT EXISTS kas_organisasi_db;
USE kas_organisasi_db;

CREATE TABLE IF NOT EXISTS kas_organisasi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipe VARCHAR(20) NOT NULL,
    nominal DOUBLE NOT NULL,
    keterangan VARCHAR(255) NOT NULL,
    sumber_dana VARCHAR(50),
    keperluan VARCHAR(50),
    tanggal DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**Opsi B: Menggunakan File SQL**
```bash
mysql -u root -p < database_kas.sql
```

### Langkah 3: Konfigurasi Koneksi Database

Edit [KoneksiDB.java](src/KoneksiDB.java):

```java
private static final String URL = "jdbc:mysql://localhost:3306/kas_organisasi_db";
private static final String USER = "root";          // ← Ubah ini
private static final String PASSWORD = "";          // ← Ubah ini
```

### Langkah 4: Kompilasi Proyek

**Windows PowerShell:**
```powershell
# Buat direktori bin jika belum ada
New-Item -ItemType Directory -Force -Path bin

# Compile
javac -d bin -cp "lib\mysql-connector-java-8.0.33.jar" src\*.java
```

**Linux/Mac:**
```bash
# Buat direktori bin
mkdir -p bin

# Compile
javac -d bin -cp "lib/mysql-connector-java-8.0.33.jar" src/*.java
```

### Langkah 5: Jalankan Aplikasi

**Windows PowerShell:**
```powershell
java -cp "bin;lib\mysql-connector-java-8.0.33.jar" MainApp
```

**Linux/Mac:**
```bash
java -cp "bin:lib/mysql-connector-java-8.0.33.jar" MainApp
```

## Untuk Pengguna VS Code

### Metode 1: Menggunakan Java Extension Pack

1. Install "Extension Pack for Java" dari VS Code Marketplace
2. Buka folder proyek di VS Code
3. Tambahkan JDBC Driver ke Referenced Libraries:
   - Buka Command Palette (Ctrl+Shift+P / Cmd+Shift+P)
   - Ketik: "Java: Configure Classpath"
   - Tambahkan file JAR MySQL JDBC dari folder `lib/`
4. Jalankan `MainApp.java` (Klik tombol "Run" atau F5)

### Metode 2: Menggunakan tasks.json (Direkomendasikan)

Buat `.vscode/tasks.json`:

```json
{
    "version": "2.0.0",
    "tasks": [
        {
            "label": "Compile Kas Organisasi",
            "type": "shell",
            "command": "javac",
            "args": [
                "-d",
                "bin",
                "-cp",
                "lib/mysql-connector-java-8.0.33.jar",
                "src/*.java"
            ],
            "group": {
                "kind": "build",
                "isDefault": true
            }
        },
        {
            "label": "Run Kas Organisasi",
            "type": "shell",
            "command": "java",
            "args": [
                "-cp",
                "bin;lib/mysql-connector-java-8.0.33.jar",
                "MainApp"
            ],
            "group": {
                "kind": "test",
                "isDefault": true
            },
            "dependsOn": ["Compile Kas Organisasi"]
        }
    ]
}
```

Kemudian:
- Tekan `Ctrl+Shift+B` untuk build
- Tekan `Ctrl+Shift+P` → "Tasks: Run Test Task" untuk run

## Masalah Umum dan Solusi

### Masalah 1: ClassNotFoundException untuk JDBC Driver

**Error:**
```
java.lang.ClassNotFoundException: com.mysql.cj.jdbc.Driver
```

**Solusi:**
- Pastikan JAR MySQL JDBC ada di folder `lib/`
- Sertakan di classpath saat kompilasi dan run
- Periksa nama file JAR sesuai dengan yang Anda gunakan

### Masalah 2: Koneksi Database Gagal

**Error:**
```
Gagal terhubung ke database!
```

**Solusi:**
1. Verifikasi server MySQL berjalan:
   ```bash
   # Windows
   net start MySQL

   # Linux
   sudo systemctl start mysql
   ```

2. Periksa kredensial di `KoneksiDB.java`
3. Verifikasi database ada:
   ```sql
   SHOW DATABASES;
   ```

### Masalah 3: Cannot Find Symbol

**Error:**
```
cannot find symbol: class Transaksi
```

**Solusi:**
- Compile semua file bersamaan:
  ```bash
  javac -d bin -cp "lib/mysql-connector-java.jar" src/*.java
  ```

## Pengujian Aplikasi

### 1. Tes Koneksi Database

Ketika Anda menjalankan aplikasi, Anda harus melihat:
```
Database terhubung dengan sukses!
```

Jika tidak, periksa service MySQL dan kredensial.

### 2. Tes Operasi Dasar

Coba operasi ini secara berurutan:

**a) Tambah Pemasukan (Iuran Anggota):**
```
Menu: 1 (Tambah Pemasukan)
Sumber Dana: 1 (Iuran)
Nominal: 500000
Keterangan: Iuran Anggota BEM
Tanggal: [Tekan Enter untuk hari ini]
```

**b) Tambah Pengeluaran (Proker):**
```
Menu: 2 (Tambah Pengeluaran)
Keperluan: 1 (Proker)
Nominal: 300000
Keterangan: Seminar Nasional
Tanggal: [Tekan Enter untuk hari ini]
```

**c) Lihat Riwayat Transaksi:**
```
Menu: 3 (Lihat Riwayat Transaksi)
```

**d) Lihat Laporan Keuangan:**
```
Menu: 5 (Lihat Laporan Keuangan)
```

Output yang diharapkan:
```
Total Pemasukan  : Rp 500.000
Total Pengeluaran: Rp 300.000
─────────────────────────────────────────────
Sisa Kas         : Rp 200.000
   Status: Kas RENDAH (< Rp 1.000.000)
```

## Daftar Periksa Persyaratan

Sebelum mengumpulkan, verifikasi:

- [ ] Semua 7 file Java dikompilasi tanpa error
- [ ] Koneksi database berhasil
- [ ] Dapat menambah pemasukan (Iuran/Sponsor/Donasi)
- [ ] Dapat menambah pengeluaran (Proker/Konsumsi/Perlengkapan)
- [ ] Dapat melihat riwayat transaksi
- [ ] Dapat menghapus transaksi
- [ ] Laporan keuangan menampilkan kalkulasi yang benar
- [ ] Penanganan exception bekerja (coba input tidak valid)
- [ ] Semua komentar kriteria UAS ada di kode

## Sumber Daya Tambahan

- **Dokumentasi MySQL:** https://dev.mysql.com/doc/
- **Tutorial Java JDBC:** https://docs.oracle.com/javase/tutorial/jdbc/
- **Java LocalDate API:** https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html

## Tips untuk Presentasi

1. **Demonstrasikan setiap persyaratan UAS:**
   - Tunjukkan hierarki inheritance (Transaksi → Pemasukan/Pengeluaran)
   - Jelaskan polymorphism di `getTipe()` dan `getKategori()`
   - Tunjukkan implementasi interface (KasDAO → ManajemenKas)
   
2. **Sorot penanganan exception:**
   - Coba masukkan teks saat angka diharapkan (InputMismatchException)
   - Tunjukkan penanganan error SQL (SQLException)
   
3. **Demonstrasikan CRUD:**
   - Create: Tambah pemasukan/pengeluaran baru
   - Read: Lihat riwayat transaksi + detail transaksi
   - Update: (Not implemented - explain architectural choice)
   - Delete: Hapus transaksi
   
4. **Tunjukkan kalkulasi:**
   - Tambah beberapa pemasukan (Iuran/Sponsor/Donasi)
   - Tambah beberapa pengeluaran (Proker/Konsumsi/Perlengkapan)
   - Tampilkan laporan keuangan dengan sisa kas

## Dukungan

Jika Anda mengalami masalah yang tidak tercakup di sini:
1. Periksa semua path file sudah benar
2. Verifikasi service MySQL berjalan
3. Pastikan versi Java adalah 8 atau lebih tinggi
4. Tinjau README.md untuk dokumentasi detail

---

**Semoga sukses dengan presentasi Anda!**
