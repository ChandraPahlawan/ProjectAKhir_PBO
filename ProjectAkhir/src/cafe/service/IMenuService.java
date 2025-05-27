package cafe.service;

import cafe.modul.MenuItem;

public interface IMenuService {
    void tambahMenu(MenuItem item);
    boolean hapusMenu(String nama);
    boolean updateHargaMenu(String nama, double harga);
    void tampilkanSemuaMenu();
    void cariMenu(String keyword);
}