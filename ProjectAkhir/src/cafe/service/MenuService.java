// package cafe.service;

// import java.util.*;
// import cafe.modul.MenuItem;

// public class MenuService {
//     private List<MenuItem> menuItems;

//     public MenuService() {
//         this.menuItems = new ArrayList<>();
//     }

//     public void tambahMenu(MenuItem item) {
//         if (item.getHarga() < 0) {
//             System.out.println("Harga tidak boleh negatif. Menu tidak ditambahkan.");
//             return;
//         }
//         menuItems.add(item);
//     }

//     public List<MenuItem> getMenuByKategori(String kategori) {
//         List<MenuItem> result = new ArrayList<>();
//         for (MenuItem item : menuItems) {
//             if (item.getKategori().equalsIgnoreCase(kategori)) {
//                 result.add(item);
//             }
//         }
//         return result;
//     }

//     public void tampilkanSemuaMenu() {
//         System.out.println("\n== Makanan ==");
//         List<MenuItem> daftarMakanan = getMenuByKategori("Makanan");
//         for (int i = 0; i < daftarMakanan.size(); i++) {
//             MenuItem item = daftarMakanan.get(i);
//             System.out.printf("%d. %s (Rp%,.2f)%n", i+1, item.getNama(), item.getHarga());
//         }

//         System.out.println("\n== Minuman ==");
//         List<MenuItem> daftarMinuman = getMenuByKategori("Minuman");
//         for (int i = 0; i < daftarMinuman.size(); i++) {
//             MenuItem item = daftarMinuman.get(i);
//             System.out.printf("%d. %s (Rp%,.2f)%n", i+1, item.getNama(), item.getHarga());
//         }
//     }

//     public void cariMenu(String keyword) {
//         System.out.println("\n=== HASIL PENCARIAN ===");
//         boolean ditemukan = false;
//         for (MenuItem item : menuItems) {
//             if (item.getNama().toLowerCase().contains(keyword.toLowerCase())) {
//                 item.tampilkanDetail();
//                 ditemukan = true;
//             }
//         }
//         if (!ditemukan) {
//             System.out.println("Menu tidak ditemukan.");
//         }
//     }

//     public MenuItem getMenuByIndex(int index) {
//         if (index >= 0 && index < menuItems.size()) {
//             return menuItems.get(index);
//         }
//         return null;
//     }

//     public boolean hapusMenu(String nama) {
//         MenuItem item = menuItems.stream()
//                 .filter(m -> m.getNama().equalsIgnoreCase(nama))
//                 .findFirst()
//                 .orElse(null);
//         if (item != null) {
//             menuItems.remove(item);
//             return true;
//         }
//         return false;
//     }

//     public boolean updateHargaMenu(String nama, double hargaBaru) {
//         MenuItem item = menuItems.stream()
//                 .filter(m -> m.getNama().equalsIgnoreCase(nama))
//                 .findFirst()
//                 .orElse(null);
//         if (item != null) {
//             item.setHarga(hargaBaru);
//             return true;
//         }
//         return false;
//     }
// }


package cafe.service;

import java.util.*;
import cafe.modul.MenuItem;

public class MenuService implements IMenuService {
    private List<MenuItem> menuItems;

    public MenuService() {
        this.menuItems = new ArrayList<>();
    }

    @Override
    public void tambahMenu(MenuItem item) {
        if (item.getHarga() < 0) {
            System.out.println("Harga tidak boleh negatif. Menu tidak ditambahkan.");
            return;
        }
        menuItems.add(item);
    }

    @Override
    public boolean hapusMenu(String nama) {
        MenuItem item = menuItems.stream()
                .filter(m -> m.getNama().equalsIgnoreCase(nama))
                .findFirst()
                .orElse(null);
        if (item != null) {
            menuItems.remove(item);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateHargaMenu(String nama, double hargaBaru) {
        MenuItem item = menuItems.stream()
                .filter(m -> m.getNama().equalsIgnoreCase(nama))
                .findFirst()
                .orElse(null);
        if (item != null) {
            item.setHarga(hargaBaru);
            return true;
        }
        return false;
    }

    @Override
    public void tampilkanSemuaMenu() {
        System.out.println("\n== Makanan ==");
        List<MenuItem> daftarMakanan = getMenuByKategori("Makanan");
        for (int i = 0; i < daftarMakanan.size(); i++) {
            MenuItem item = daftarMakanan.get(i);
            System.out.printf("%d. %s (Rp%,.2f)%n", i+1, item.getNama(), item.getHarga());
        }

        System.out.println("\n== Minuman ==");
        List<MenuItem> daftarMinuman = getMenuByKategori("Minuman");
        for (int i = 0; i < daftarMinuman.size(); i++) {
            MenuItem item = daftarMinuman.get(i);
            System.out.printf("%d. %s (Rp%,.2f)%n", i+1, item.getNama(), item.getHarga());
        }
    }

    @Override
    public void cariMenu(String keyword) {
        System.out.println("\n=== HASIL PENCARIAN ===");
        boolean ditemukan = false;
        for (MenuItem item : menuItems) {
            if (item.getNama().toLowerCase().contains(keyword.toLowerCase())) {
                item.tampilkanDetail();
                ditemukan = true;
            }
        }
        if (!ditemukan) {
            System.out.println("Menu tidak ditemukan.");
        }
    }

    // Method tambahan yang tidak ada di interface tapi diperlukan
    public List<MenuItem> getMenuByKategori(String kategori) {
        List<MenuItem> result = new ArrayList<>();
        for (MenuItem item : menuItems) {
            if (item.getKategori().equalsIgnoreCase(kategori)) {
                result.add(item);
            }
        }
        return result;
    }

    public MenuItem getMenuByIndex(int index) {
        if (index >= 0 && index < menuItems.size()) {
            return menuItems.get(index);
        }
        return null;
    }
}