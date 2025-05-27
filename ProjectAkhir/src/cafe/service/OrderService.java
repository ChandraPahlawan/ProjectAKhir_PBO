package cafe.service;

import cafe.modul.MenuItem;
import cafe.modul.Pesanan;
import cafe.modul.User;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private List<Pesanan> daftarPesanan;
    
    public OrderService() {
        this.daftarPesanan = new ArrayList<>();
    }
    
    public void buatPesanan(User user, List<MenuItem> items) {

        Pesanan pesanan = new Pesanan(user);
        for (MenuItem item : items) {
            pesanan.tambahItem(item);
        }
        daftarPesanan.add(pesanan);
        System.out.println("Pesanan berhasil dibuat!");
    }
    
    public List<Pesanan> getPesananByUser(User user) {
        List<Pesanan> result = new ArrayList<>();
        for (Pesanan p : daftarPesanan) {
            if (p.getUser().equals(user)) {
                result.add(p);
            }
        }
        return result;
    }
    
    public List<Pesanan> getSemuaPesanan() {
        return new ArrayList<>(daftarPesanan);
    }
    
    public void updateStatusPesanan(String idPesanan, String status) {
        for (Pesanan p : daftarPesanan) {
            if (p.getId().equals(idPesanan)) {
                p.setStatus(status);
                System.out.println("Status pesanan " + idPesanan + " diubah menjadi: " + status);
                return;
            }
        }
        System.out.println("Pesanan dengan ID " + idPesanan + " tidak ditemukan");
    }
}