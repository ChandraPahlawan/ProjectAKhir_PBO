// Import dari Java Standard Library
import java.util.List;
import java.util.ArrayList; 

// Import dari package cafe
import cafe.modul.Admin;
import cafe.modul.Makanan;
import cafe.modul.Minuman;
import cafe.modul.Pesanan;
import cafe.modul.User;
import cafe.modul.MenuItem; 
import cafe.service.AuthService;
import cafe.service.MenuService;
import cafe.service.OrderService;
import cafe.util.InputUtil;
import cafe.util.PrinterUtil;

public class App {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        MenuService menuService = new MenuService();
        OrderService orderService = new OrderService();
        
        // Data admin default
        authService.registerAdmin(new Admin("admin", "admin123"));
        
        // Contoh data menu
        menuService.tambahMenu(new Makanan("Nasi Goreng", 25000, "Makanan"));
        menuService.tambahMenu(new Makanan("Mix Plate", 25000, "Makanan"));
        menuService.tambahMenu(new Makanan("Chicken Wings", 35000, "Makanan"));
        menuService.tambahMenu(new Makanan("Burger", 20000, "Makanan"));
        menuService.tambahMenu(new Makanan("Steak", 125000, "Makanan"));
        menuService.tambahMenu(new Minuman("Air Mineral", 7000, "Minuman"));   
        menuService.tambahMenu(new Minuman("Es Teh", 8000, "Minuman"));
        menuService.tambahMenu(new Minuman("Ocean Blue", 20000, "Minuman"));       
        menuService.tambahMenu(new Minuman("Cappucino", 25000, "Minuman"));
        menuService.tambahMenu(new Minuman("Butterscoth Arren Latte", 20000, "Minuman"));       
        
        // Jalankan aplikasi
        new CafeApplication(authService, menuService, orderService).run();
    }
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                System.out.print("\033[H\033[2J");
        } catch (Exception e) {
            System.out.println("Tidak bisa membersihkan layar.");
        }
        
    }
    public static void showSpinner(int durationMillis) {
        char[] spinner = {'|', '/', '-', '\\'};
        long start = System.currentTimeMillis();
        int i = 0;
        while (System.currentTimeMillis() - start < durationMillis) {
            System.out.print("\rMemproses " + spinner[i++ % spinner.length]);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\r             ");
    }

    public static void jeda(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.out.println("Jeda terganggu.");
        }
    }
}   

class CafeApplication {
    private AuthService authService;
    private MenuService menuService;
    private OrderService orderService;
    private User currentUser;

    public CafeApplication(AuthService authService, MenuService menuService, 
                         OrderService orderService) {
        this.authService = authService;
        this.menuService = menuService;
        this.orderService = orderService;
    }

    public void run() {
        App.clearScreen();
        System.out.println("=== SELAMAT DATANG DI SISTEM MANAJEMEN CAFE ===");
        App.showSpinner(1000);
        while (true) {
            if (currentUser == null) {
                showAuthMenu();
            } else if (currentUser instanceof Admin) {
                showAdminMenu();
            } else {
                showUserMenu();
            }
        }
    }

    private void showAuthMenu() {
        App.clearScreen();
        System.out.println("==============================================");
        System.out.println("=== SELAMAT DATANG DI SISTEM MANAJEMEN CAFE ===");
        System.out.println("==============================================");
        System.out.println("\n========== MENU AUTENTIKASI ==========");
        System.out.println("\n1. Login");
        System.out.println("2. Registrasi (User)");
        System.out.println("0. Keluar");
        System.out.print("--------------------------------------\nPilihan: ");

        int choice = InputUtil.getIntInput();
        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                registerUser();
                break;
            case 0:
                System.exit(0);
                App.showSpinner(1500);
            default:
                System.out.println("Pilihan tidak valid!");
                App.jeda(1500);
        }
    }

    private void login() {
        App.clearScreen();
        System.out.println("\n========== LOGIN ==========");
        App.showSpinner(1500);
        System.out.println("MASUKKAN USERNAME SERTA PASSWORD ANDA");
        System.out.print("Username: ");
        String username = InputUtil.getStringInput();
        System.out.print("Password: ");
        String password = InputUtil.getStringInput();

        currentUser = authService.login(username, password);
        if (currentUser != null) {
            System.out.println("\n========================= BERHASIL =========================");
            System.out.println("Login berhasil sebagai " + 
                (currentUser instanceof Admin ? "Admin" : "User"));
            System.out.println("========================= BERHASIL =========================");
                App.showSpinner(1500);
        } else {
            System.out.println("\n============================= WARNING =============================");
            System.out.println("Username atau password salah!");
            System.out.println("============================= WARNING =============================");
                App.jeda(1500);
        }
    }

private void registerUser() {
    App.clearScreen();
    System.out.println("\n========== REGISTER ==========");
    App.showSpinner(1500);
    System.out.println("MASUKKAN USERNAME SERTA PASSWORD BARU ANDA");

    System.out.print("Username: ");
    String username = InputUtil.getStringInput();
    System.out.print("Password: ");
    String password = InputUtil.getStringInput();
    System.out.print("Nama Lengkap: ");
    String fullName = InputUtil.getStringInput();

    // Validasi input kosong atau hanya spasi
    if (username.trim().isEmpty() || password.trim().isEmpty() || fullName.trim().isEmpty()) {
        System.out.println("\n========================= WARNING =========================");
        System.out.println("Input tidak boleh kosong atau hanya berisi spasi!");
        System.out.println("========================= WARNING =========================");
        App.jeda(1500);
        return;
    }

    // Cek username "admin" tidak diperbolehkan
    if (username.equalsIgnoreCase("admin")) {
        System.out.println("\n============================= WARNING =============================");
        System.out.println("Username 'admin' tidak diperbolehkan. Silakan pilih username lain.");
        System.out.println("============================= WARNING =============================");
        App.jeda(1500);
        return;
    }

    // Proses registrasi
    if (authService.registerUser(new User(username, password, fullName))) {
        System.out.println("\n========================= BERHASIL =========================");
        System.out.println("Registrasi berhasil! Silakan login.");
        System.out.println("========================= BERHASIL =========================");
        App.jeda(1500);
    } else {
        System.out.println("\n========================= WARNING =========================");
        System.out.println("Username sudah digunakan!");
        System.out.println("========================= WARNING =========================");
        App.jeda(1500);
    }
}



    private void showAdminMenu() {
        App.clearScreen();
        System.out.println("\n=== MENU ADMIN ===");
        System.out.println("1. Kelola Menu");
        System.out.println("2. Lihat Pesanan");
        System.out.println("3. Update Status Pesanan");
        System.out.println("4. Logout");
        System.out.print("--------------------------------\nPilihan: ");

        int choice = InputUtil.getIntInput();
        switch (choice) {
            case 1:
                manageMenu();
                break;
            case 2:
                viewOrders();
                break;
            case 3:
                updateOrderStatus();
                break;
            case 4:
                currentUser = null;
                App.showSpinner(2000);
                break;
            default:
                System.out.println("==============================");
                System.out.println("Pilihan tidak valid!");
                System.out.println("==============================");                
                App.jeda(2000);
        }
    }

    private void showUserMenu() {
        App.clearScreen();
        System.out.println("\n=========== MENU USER ===========");
        System.out.println("1. Lihat Menu");
        System.out.println("2. Cari Menu");
        System.out.println("3. Buat Pesanan");
        System.out.println("4. Lihat Pesanan Saya");
        System.out.println("5. Logout");
        System.out.print("----------------------------------\nPilihan: ");

        int choice = InputUtil.getIntInput();
        switch (choice) {
            case 1:
                viewMenu();
                break;
            case 2:
                searchMenu();
                break;
            case 3:
                createOrder();
                break;
            case 4:
                viewMyOrders();
                break;
            case 5:
                currentUser = null;
                App.showSpinner(2000);
                break;
            default:
                System.out.println("==============================");
                System.out.println("Pilihan tidak valid!");
                System.out.println("==============================");                
                App.jeda(2000);        }
    }

    // ======= Implementasi Fitur Kelola Menu =======
    private void manageMenu() {
        while (true) {
            App.clearScreen();
            System.out.println("\n=========== KELOLA MENU ===========");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Hapus Menu");
            System.out.println("3. Update Harga Menu");
            System.out.println("4. Lihat Semua Menu");
            System.out.println("0. Kembali");
            System.out.print("----------------------------------\nPilihan: ");

            int choice = InputUtil.getIntInput();
            switch (choice) {
                case 1:
                    tambahMenuBaru();
                    break;
                case 2:
                    hapusMenu();
                    break;
                case 3:
                    updateHargaMenu();
                    break;
                case 4:
                    viewMenu();
                    break;
                case 0:
                    return; // keluar dari manageMenu ke menu admin utama
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private void tambahMenuBaru() {
        App.clearScreen();
        System.out.println("\n=== DAFTAR MENU SAAT INI ===");
        menuService.tampilkanSemuaMenu();
        System.out.print("\n--------------------------------------");
        System.out.print("\nMasukkan nama menu: ");
        String nama = InputUtil.getStringInput();
        System.out.print("Masukkan harga: ");
        double harga = InputUtil.getDoubleInput();

        String pilihan;
        String kategori = null;

            while (true) {
                System.out.print("""
                    Kategori:
                    1. Makanan
                    2. Minuman
                    Pilih Kategori (1/2): 
                    """);

                pilihan = InputUtil.getStringInput();
                if (pilihan.equals("1")) {
                    kategori = "Makanan";
                    break;
                } else if (pilihan.equals("2")) {
                    kategori = "Minuman";
                    break;
                } else {
                    System.out.println("===============================================");
                    System.out.println("Pilihan tidak valid! Silakan masukkan 1 atau 2.");
                    System.out.println("===============================================");
                    App.jeda(1500);
                }
            }

        // Tambahkan sesuai objek turunan
        switch (kategori.toLowerCase()) {
            case "makanan" -> menuService.tambahMenu(new Makanan(nama, harga, kategori));
            case "minuman" -> menuService.tambahMenu(new Minuman(nama, harga, kategori));
            default -> {
                System.out.print("\n--------------------------------------");
                System.out.println("\nKategori tidak dikenali.");
                System.out.print("--------------------------------------");
                return;
            }
        }
        System.out.print("\n--------------------------------------");
        System.out.println("\nMenu berhasil ditambahkan!");
        System.out.print("--------------------------------------");
        App.jeda(1500);
        App.clearScreen();
    }

private void hapusMenu() {
    App.clearScreen();
    System.out.println("\n=== DAFTAR MENU SAAT INI ===");
    menuService.tampilkanSemuaMenu(); // Tampilkan menu dulu sebelum hapus
    
    boolean berhasil = false;
    while (!berhasil) {
        System.out.print("--------------------------------------");
        System.out.print("\nMasukkan nama menu yang ingin dihapus: ");
        String nama = InputUtil.getStringInput();

        berhasil = menuService.hapusMenu(nama);
        if (berhasil) {
            System.out.println("\n===================================");
            System.out.println("Menu \"" + nama + "\" berhasil dihapus.");
            System.out.println("===================================");
            App.jeda(1500);
        } else {
            System.out.println("===================================");
            System.out.println("Menu dengan nama \"" + nama + "\" tidak ditemukan. Silakan coba lagi.");
            System.out.println("===================================");
            App.jeda(1500);
        }
    }

    System.out.print("\nTekan ENTER untuk kembali...");
    InputUtil.getStringInput();
    App.clearScreen();
}

private void updateHargaMenu() {
    App.clearScreen();
    System.out.println("\n=== DAFTAR MENU SAAT INI ===");
    menuService.tampilkanSemuaMenu();

    boolean berhasil = false;
    String nama;
    double hargaBaru;

    while (!berhasil) {
        System.out.print("\nMasukkan nama menu yang ingin diupdate: ");
        nama = InputUtil.getStringInput();

        System.out.print("Masukkan harga baru: ");
        hargaBaru = InputUtil.getDoubleInput();

        // Validasi harga
        if (hargaBaru <= 0) {
            System.out.print("\n----------------------------------------------------------");
            System.out.println("Harga tidak boleh nol atau negatif. Silakan masukkan ulang.");
                System.out.print("----------------------------------------------------------");
            continue;
        }

        berhasil = menuService.updateHargaMenu(nama, hargaBaru);
        if (berhasil) {
            System.out.println("\n==================================================================");
            System.out.println("Harga menu \"" + nama + "\" berhasil diperbarui menjadi Rp" + hargaBaru);
            System.out.println("==================================================================");
            App.jeda(1500);
        } else {
            System.out.println("\n=================================================================");
            System.out.println("Menu dengan nama \"" + nama + "\" tidak ditemukan. Silakan coba lagi.");
            System.out.println("=================================================================");
            App.jeda(1500);
        }
    }

    System.out.print("\nTekan ENTER untuk kembali...");
    InputUtil.getStringInput();
    App.clearScreen();
}



    // ======= Fitur lain yang sudah ada =======
    private void viewMenu() {
        App.showSpinner(1500);
        menuService.tampilkanSemuaMenu();
        System.out.print("\nTekan ENTER untuk kembali...");
        InputUtil.getStringInput();
    }

    private void searchMenu() {
        System.out.print("Masukkan kata kunci: ");
        String keyword = InputUtil.getStringInput();
        menuService.cariMenu(keyword);
        System.out.print("\nTekan ENTER untuk kembali...");
        InputUtil.getStringInput();
    }

    private void createOrder() {
        App.clearScreen();
        List<MenuItem> itemsDipilih = new ArrayList<>();
        while (true) {
            App.clearScreen();
            System.out.println("\n=== BUAT PESANAN ===");
            menuService.tampilkanSemuaMenu();
            System.out.println("\nKategori:");
            System.out.println("1. Makanan");
            System.out.println("2. Minuman");
            System.out.println("0. Selesai");
            System.out.print("Pilihan kategori (1/2/0): ");
            int kategoriPilihan = InputUtil.getIntInput();
            if (kategoriPilihan == 0) {
                App.clearScreen();
                break;
            }
            List<MenuItem> daftarKategori;
            if (kategoriPilihan == 1) {
                daftarKategori = menuService.getMenuByKategori("Makanan");
            } else if (kategoriPilihan == 2) {
                daftarKategori = menuService.getMenuByKategori("Minuman");
            } else {
                System.out.println("\n============================");
                System.out.println("Kategori tidak valid!");
                System.out.println("============================");
                App.jeda(2000);
                continue;
            }
            System.out.print("Pilih nomor menu (0 = batal): ");
            int nomorMenu = InputUtil.getIntInput();
            if (nomorMenu == 0) {
                continue;
            }
            if (nomorMenu < 1 || nomorMenu > daftarKategori.size()) {
                System.out.println("\n============================");
                System.out.println("Nomor menu tidak valid!");
                System.out.println("============================");
            } else {
                MenuItem itemDipilih = daftarKategori.get(nomorMenu - 1);
                itemsDipilih.add(itemDipilih);
                System.out.println("\n============================");
                System.out.println(itemDipilih.getNama() + " ditambahkan ke pesanan.");
                System.out.println("============================");
                App.jeda(1500);
            }
        }
        if (itemsDipilih.isEmpty()) {
            System.out.println("\n============================");
            System.out.println("Anda belum memilih item apapun.");
            System.out.println("============================");
            return;
        }
        orderService.buatPesanan(currentUser, itemsDipilih);
        List<Pesanan> semuaPesanan = orderService.getSemuaPesanan();
        Pesanan pesananTerakhir = semuaPesanan.get(semuaPesanan.size() - 1);
        PrinterUtil.cetakStruk(pesananTerakhir);
        System.out.print("\nTekan ENTER untuk kembali...");
        InputUtil.getStringInput();
    }

    private void viewOrders() {
        App.clearScreen();
        System.out.println("\n=== DAFTAR PESANAN ===");
        List<Pesanan> semuaPesanan = orderService.getSemuaPesanan();
        if (semuaPesanan.isEmpty()) {
            System.out.println("Belum ada pesanan");
        } else {
            for (Pesanan p : semuaPesanan) {
                System.out.printf("ID: %s | User: %s | Total: Rp%,.2f | Status: %s%n",
                    p.getId(), p.getUser().getFullName(), p.getTotalHarga(), p.getStatus());
            }
        }
        System.out.print("\nTekan ENTER untuk kembali...");
        InputUtil.getStringInput();
    }

    private void updateOrderStatus() {
    App.clearScreen();
    viewOrders();

    System.out.print("\nMasukkan ID Pesanan yang akan diupdate: ");
    String idPesanan = InputUtil.getStringInput();

    String statusBaru;

    while (true) {
        System.out.println("""
                
                === Pilih Status Baru ===
                1. Dibuat
                2. Selesai
                3. Batal
                """);
        System.out.print("Pilih status (1/2/3): ");
        String pilihan = InputUtil.getStringInput();

        switch (pilihan) {
            case "1" -> statusBaru = "Dibuat";
            case "2" -> statusBaru = "Selesai";
            case "3" -> statusBaru = "Batal";
            default -> {
                System.out.println("Pilihan tidak valid! Masukkan 1, 2, atau 3.");
                App.jeda(1500);
                continue;
            }
        }
        break; // keluar dari while jika input valid
    }

    orderService.updateStatusPesanan(idPesanan, statusBaru);
    System.out.println("Status pesanan berhasil diperbarui.");
    App.showSpinner(1000);
    System.out.print("\nTekan ENTER untuk kembali...");
    InputUtil.getStringInput();
}



    private void viewMyOrders() {
        System.out.println("\n=== PESANAN SAYA ===");
        List<Pesanan> pesananSaya = orderService.getPesananByUser(currentUser);
        if (pesananSaya.isEmpty()) {
            System.out.println("Anda belum memiliki pesanan");
        } else {
            for (Pesanan p : pesananSaya) {
                System.out.println("===========================================");
                System.out.printf("ID: %s | Total: Rp%,.2f | Status: %s%n",
                    p.getId(), p.getTotalHarga(), p.getStatus());
                System.out.println("===========================================");
                System.out.print("\nTekan ENTER untuk kembali...");
                InputUtil.getStringInput();
            }
        }
    }
}