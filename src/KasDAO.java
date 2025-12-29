import java.util.List;

// Implementasi Interface]
public interface KasDAO {
    
    // Operasi CRUD
    
    // Create
    void tambahTransaksi(Transaksi transaksi) throws Exception;
    
    // Read
    List<Transaksi> getAllTransaksi() throws Exception;
    Transaksi getTransaksiById(int id) throws Exception;
    
    // Delete
    void hapusTransaksi(int id) throws Exception;
    
    // Perhitungan Matematika
    double getTotalPemasukan() throws Exception;
    double getTotalPengeluaran() throws Exception;
    double getSisaKas() throws Exception;
}
