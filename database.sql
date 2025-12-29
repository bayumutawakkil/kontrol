-- Database Schema untuk Aplikasi Kas Organisasi (UAS PBO)
-- [UAS Point: Database Design - JDBC]

CREATE DATABASE IF NOT EXISTS kas_organisasi_db;
USE kas_organisasi_db;

CREATE TABLE IF NOT EXISTS kas_organisasi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipe VARCHAR(20) NOT NULL,           -- 'PEMASUKAN' atau 'PENGELUARAN'
    nominal DOUBLE NOT NULL,
    keterangan VARCHAR(255) NOT NULL,
    sumber_dana VARCHAR(100),            -- Untuk Pemasukan: 'Iuran', 'Sponsor', 'Donasi'
    keperluan VARCHAR(100),              -- Untuk Pengeluaran: 'Proker', 'Konsumsi', 'Perlengkapan'
    tanggal DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Sample Data
INSERT INTO kas_organisasi (tipe, nominal, keterangan, sumber_dana, keperluan, tanggal) VALUES
('PEMASUKAN', 5000000, 'Iuran Anggota Semester Genap', 'Iuran Anggota', NULL, '2025-01-10'),
('PEMASUKAN', 3000000, 'Sponsor PT Telkomsel', 'Sponsor Perusahaan', NULL, '2025-01-15'),
('PENGELUARAN', 1500000, 'Biaya Seminar Nasional', NULL, 'Program Kerja', '2025-01-20'),
('PENGELUARAN', 800000, 'Konsumsi Rapat Perdana', NULL, 'Konsumsi Rapat', '2025-01-22');
