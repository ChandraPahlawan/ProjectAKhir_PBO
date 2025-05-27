package cafe.modul;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pesanan {
    private String id;
    private User user;
    private List<MenuItem> items;
    private Date tanggal;
    private String status;
    private static int counter = 1;
    
    public Pesanan(User user) {
        String last5Digits = String.format("%03d", counter++);
        this.id = "ORD-" + last5Digits;
        this.user = user;
        this.items = new ArrayList<>();
        this.tanggal = new Date();
        this.status = "Menunggu";
    }
    
    public void tambahItem(MenuItem item) {
        items.add(item);
    }
    
    // Tambahkan getter methods
    public String getId() {
        return id;
    }
    
    public User getUser() {
        return user;
    }
    
    public List<MenuItem> getItems() {
        return items;
    }
    
    public Date getTanggal() {
        return tanggal;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public double getTotalHarga() {
        return items.stream().mapToDouble(MenuItem::getHarga).sum();
    }
}