import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Interface Implementation
public class ManajemenKas implements KasDAO {
    
    // JDBC - CREATE Operation
    @Override
    public void tambahTransaksi(Transaksi transaksi) throws Exception {
        String sql = "INSERT INTO kas_organisasi (tipe, nominal, keterangan, sumber_dana, keperluan, tanggal) VALUES (?, ?, ?, ?, ?, ?)";
        
        // Exception Handling - try-with-resources
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, transaksi.getTipe());
            pstmt.setDouble(2, transaksi.getNominal());
            pstmt.setString(3, transaksi.getKeterangan());
            
            // Branching - Logika if-else
            // Polymorphism - Pengecekan instanceof
            if (transaksi instanceof Pemasukan pemasukan) {
                pstmt.setString(4, pemasukan.getSumberDana());
                pstmt.setNull(5, Types.VARCHAR);
            } else if (transaksi instanceof Pengeluaran pengeluaran) {
                pstmt.setNull(4, Types.VARCHAR);
                pstmt.setString(5, pengeluaran.getKeperluan());
            }
            
            pstmt.setDate(6, new java.sql.Date(transaksi.getTanggal().getTime()));
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            // Exception Handling - Penanganan error
            throw new Exception("Gagal menambah transaksi: " + e.getMessage());
        }
    }
    
    // JDBC - READ Operation
    // Collection Framework - ArrayList usage
    @Override
    public List<Transaksi> getAllTransaksi() throws Exception {
        List<Transaksi> daftarTransaksi = new ArrayList<>();
        String sql = "SELECT * FROM kas_organisasi ORDER BY tanggal DESC";
        
        try (Connection conn = KoneksiDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            // Loop - while untuk iterasi ResultSet
            while (rs.next()) {
                int id = rs.getInt("id");
                String tipe = rs.getString("tipe");
                double nominal = rs.getDouble("nominal");
                String keterangan = rs.getString("keterangan");
                Date tanggal = rs.getDate("tanggal");
                
                // Polymorphism - Pembuatan object berdasarkan tipe
                Transaksi transaksi;
                if ("PEMASUKAN".equalsIgnoreCase(tipe)) {
                    String sumberDana = rs.getString("sumber_dana");
                    transaksi = new Pemasukan(id, nominal, keterangan, tanggal, sumberDana);
                } else {
                    String keperluan = rs.getString("keperluan");
                    transaksi = new Pengeluaran(id, nominal, keterangan, tanggal, keperluan);
                }
                
                daftarTransaksi.add(transaksi);
            }
            
        } catch (SQLException e) {
            throw new Exception("Gagal mengambil data: " + e.getMessage());
        }
        
        return daftarTransaksi;
    }
    
    // JDBC - READ by ID
    @Override
    public Transaksi getTransaksiById(int id) throws Exception {
        String sql = "SELECT * FROM kas_organisasi WHERE id = ?";
        
        // Diperbaiki: Menambahkan ResultSet ke try-with-resources untuk memastikan penutupan yang benar
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String tipe = rs.getString("tipe");
                    double nominal = rs.getDouble("nominal");
                    String keterangan = rs.getString("keterangan");
                    Date tanggal = rs.getDate("tanggal");
                    
                    if ("PEMASUKAN".equalsIgnoreCase(tipe)) {
                        String sumberDana = rs.getString("sumber_dana");
                        return new Pemasukan(id, nominal, keterangan, tanggal, sumberDana);
                    } else {
                        String keperluan = rs.getString("keperluan");
                        return new Pengeluaran(id, nominal, keterangan, tanggal, keperluan);
                    }
                }
            }
            
        } catch (SQLException e) {
            throw new Exception("Gagal mengambil transaksi: " + e.getMessage());
        }
        
        return null;
    }
    
    // JDBC - DELETE Operation
    @Override
    public void hapusTransaksi(int id) throws Exception {
        String sql = "DELETE FROM kas_organisasi WHERE id = ?";
        
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new Exception("Transaksi dengan ID " + id + " tidak ditemukan.");
            }
            
        } catch (SQLException e) {
            throw new Exception("Gagal menghapus transaksi: " + e.getMessage());
        }
    }
    
    // Math Calculation - Penjumlahan pemasukan
    @Override
    public double getTotalPemasukan() throws Exception {
        String sql = "SELECT SUM(nominal) as total FROM kas_organisasi WHERE tipe = 'PEMASUKAN'";
        
        try (Connection conn = KoneksiDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            throw new Exception("Gagal menghitung total pemasukan: " + e.getMessage());
        }
        
        return 0.0;
    }
    
    // Math Calculation - Penjumlahan pengeluaran
    @Override
    public double getTotalPengeluaran() throws Exception {
        String sql = "SELECT SUM(nominal) as total FROM kas_organisasi WHERE tipe = 'PENGELUARAN'";
        
        try (Connection conn = KoneksiDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
            
        } catch (SQLException e) {
            throw new Exception("Gagal menghitung total pengeluaran: " + e.getMessage());
        }
        
        return 0.0;
    }
    
    // Math Calculation - Sisa kas
    @Override
    public double getSisaKas() throws Exception {
        // Math Logic - Logika perhitungan
        return getTotalPemasukan() - getTotalPengeluaran();
    }
}
