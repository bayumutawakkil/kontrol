import java.util.Date;

// Inheritance - Subclass extends Abstract Parent
public class Pengeluaran extends Transaksi {
    
    // Encapsulation - Field private khusus untuk Pengeluaran
    private String keperluan; // Proker, Konsumsi, Perlengkapan
    
    // Constructor with super()
    public Pengeluaran(int id, double nominal, String keterangan, Date tanggal, String keperluan) {
        super(id, nominal, keterangan, tanggal);
        this.keperluan = keperluan;
    }
    
    // Encapsulation - Getter & Setter
    public String getKeperluan() {
        return keperluan;
    }
    
    public void setKeperluan(String keperluan) {
        this.keperluan = keperluan;
    }
    
    // Polymorphism - Method overriding
    @Override
    public String getTipe() {
        return "PENGELUARAN";
    }
    
    // Polymorphism - Method overriding
    @Override
    public String getKategori() {
        return keperluan;
    }
}
