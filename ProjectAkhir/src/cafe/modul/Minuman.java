package cafe.modul;

public class Minuman extends MenuItem {
    public Minuman(String nama, double harga, String kategori) {
        super(nama, harga, kategori);
    }
    
    @Override
    public void tampilkanDetail() {
        System.out.println(getNama() + " - Rp" + getHarga());
    }
}