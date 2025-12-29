import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// Inheritance - Abstract Superclass
// Encapsulation - Class with private fields
public abstract class Transaksi {
    
    // Encapsulation - Private attributes
    private int id;
    private double nominal;
    private String keterangan;
    private Date tanggal;
    
    // Constructor Implementation
    public Transaksi(int id, double nominal, String keterangan, Date tanggal) {
        this.id = id;
        this.nominal = nominal;
        this.keterangan = keterangan;
        this.tanggal = tanggal;
    }
    
    // Encapsulation - Getter methods
    public int getId() {
        return id;
    }
    
    public double getNominal() {
        return nominal;
    }
    
    public String getKeterangan() {
        return keterangan;
    }
    
    public Date getTanggal() {
        return tanggal;
    }
    
    // Encapsulation - Setter methods
    public void setId(int id) {
        this.id = id;
    }
    
    public void setNominal(double nominal) {
        this.nominal = nominal;
    }
    
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    
    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }
    
    // Polymorphism - Method abstract yang harus di-override
    public abstract String getTipe();
    public abstract String getKategori();
    
    // String Manipulation - Format tanggal
    public String getTanggalFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(tanggal);
    }
    
    // String Manipulation - Format mata uang
    public String getNominalFormatted() {
        // Diperbaiki: Menggunakan Locale constructor untuk kompatibilitas Java 8+
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatRupiah.format(nominal);
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s - %s: %s | %s", 
            getTanggalFormatted(), 
            getTipe(), 
            getKategori(),
            getNominalFormatted(), 
            keterangan);
    }
}
