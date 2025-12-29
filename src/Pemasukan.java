import java.util.Date;

// Inheritance - Subclass extends Abstract Parent
public class Pemasukan extends Transaksi {
    
    // Encapsulation - Field private khusus untuk Pemasukan
    private String sumberDana; // Iuran, Sponsor, Donasi
    
    // Constructor with super()
    public Pemasukan(int id, double nominal, String keterangan, Date tanggal, String sumberDana) {
        super(id, nominal, keterangan, tanggal);
        this.sumberDana = sumberDana;
    }
    
    // Encapsulation - Getter & Setter
    public String getSumberDana() {
        return sumberDana;
    }
    
    public void setSumberDana(String sumberDana) {
        this.sumberDana = sumberDana;
    }
    
    // Polymorphism - Method overriding
    @Override
    public String getTipe() {
        return "PEMASUKAN";
    }
    
    // Polymorphism - Method overriding
    @Override
    public String getKategori() {
        return sumberDana;
    }
}
