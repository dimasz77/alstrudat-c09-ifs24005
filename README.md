# alstrudat-c09-ifs24005

# OS Scheduler: Stable & Updatable Min Heap

## Description
Kamu ditugaskan membangun inti dari sistem penjadwalan proses pada Sistem Operasi menggunakan **Minimum Binary Heap** berbasis Array (indeks dimulai dari 1). 

Kapasitas awal heap adalah **3**. Jika penuh, heap harus diperbesar otomatis dengan rumus: `New_Capacity = (Current_Capacity * 2) + 1`.

**Aturan Penjadwalan (CRITICAL RULES):**
1. Semakin **KECIL** angka prioritas, semakin cepat dieksekusi.
2. **Stabilitas (Tie-Breaker):** Jika ada dua proses dengan prioritas yang SAMA, maka proses yang **didaftarkan lebih awal** (berdasarkan urutan `ADD`) harus dilayani lebih dulu. 
3. **Pelacakan Indeks:** Semua pencarian elemen untuk `UPDATE` dan `KILL` harus dilakukan secara efisien ($O(1)$ atau $O(N)$), dan perbaikan struktur (Percolate Up/Down) dalam waktu $O(\log N)$. Setiap kali elemen bertukar posisi (*swap*), pastikan pelacak posisinya juga di-*update*!

**Daftar Perintah:**
- `ADD [Nama] [Prio]` : Menambah proses. Jika nama sudah ada, cetak: `ADD FAILED`.
- `UPDATE [Nama] [Prio_Baru]` : Mengubah prioritas. Tentukan apakah elemen harus *Percolate Up* atau *Percolate Down*. Jika tidak ada, cetak: `UPDATE FAILED`.
- `KILL [Nama]` : Menghapus proses secara paksa. Cetak: `KILLED: [Nama]`. Jika tidak ada, cetak: `KILL FAILED`. *(Hint: Tukar elemen yang akan dihapus dengan elemen terakhir di heap, kurangi ukuran heap, lalu evaluasi apakah penggantinya harus naik atau turun!)*
- `SERVE` : Mengekstrak root. Cetak: `SERVED: [Nama]`. Jika kosong, cetak: `SERVED EMPTY`.
- `PRINT` : Mencetak seluruh `[Nama]` di array heap (dari indeks 1 ke N) dipisah spasi. Jika kosong, cetak: `EMPTY`.

## Source Codes
| No | File | Deskripsi |
|---|---|---|
| 1 | App.java | Mengurus Parsing Input/Output (Sudah disediakan) |
| 2 | Program.java | Tempat mengimplementasikan logika Scheduler |

## Test Cases
| No | File Input | Tujuan Uji |
|---|---|---|
| 1 | input1.txt | Uji Dasar (ADD, SERVE, PRINT) |
| 2 | input2.txt | Uji Resize Kapasitas & Stabilitas (Tie-Breaker) |
| 3 | input3.txt | Uji Jebakan UPDATE (Percolate Up & Down di tengah Tree) |
| 4 | input4.txt | Uji Jebakan KILL (Arbitrary Deletion) |
| 5 | input5.txt | Uji Edge Cases (Data Kosong, Duplikat, Tidak Ditemukan) |