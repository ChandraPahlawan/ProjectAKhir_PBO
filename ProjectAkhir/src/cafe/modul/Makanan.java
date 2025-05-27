package cafe.modul;

public class Makanan extends MenuItem {
    public Makanan(String nama, double harga, String kategori) {
        super(nama, harga, kategori);
    }
    
    @Override
    public void tampilkanDetail() {
        System.out.println(getNama() + " - Rp" + getHarga());
    }
}