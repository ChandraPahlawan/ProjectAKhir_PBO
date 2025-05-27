package cafe.util;

import cafe.modul.Pesanan;
import cafe.modul.MenuItem;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrinterUtil {
    public static void cetakStruk(Pesanan pesanan) {
        System.out.println("\n=== STRUK PEMBAYARAN ===");
        System.out.println("Tanggal: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
        System.out.println("Pelanggan: " + pesanan.getUser().getFullName());
        System.out.println("----------------------------------");
        
        for (MenuItem item : pesanan.getItems()) {
            System.out.printf("%-15s Rp%,d%n", item.getNama(), (int)item.getHarga());
        }
        
        System.out.println("----------------------------------");
        System.out.printf("TOTAL: Rp%,d%n", (int)pesanan.getTotalHarga());
        System.out.println("Terima kasih telah berkunjung!");
    }
}