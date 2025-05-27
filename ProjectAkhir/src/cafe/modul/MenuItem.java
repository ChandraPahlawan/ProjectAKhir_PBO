package cafe.modul;

public abstract class MenuItem {
    private String nama;
    private double harga;
    private String kategori;
    
    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }
    
    // Getter methods
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public String getKategori() { return kategori; }
    
    public abstract void tampilkanDetail();
    
    public void setNama(String nama) {
        this.nama = nama;
    }
    
    public void setHarga(double harga) {
        this.harga = harga;
    }
    
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}