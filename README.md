# 📚 Matchagipi

**Matchagipi** adalah sistem manajemen perpustakaan digital berbasis Java, dibangun menggunakan Java Swing di NetBeans. Mendukung berbagai peran pengguna seperti **Admin**, **Staff**, dan **Member**.

---

## ✨ Fitur Berdasarkan Role

### 🔐 Login & Autentikasi
- Login untuk semua pengguna
- Registrasi akun baru

### 🧑‍💼 Admin
- Manajemen user (staff & anggota)
- Manajemen buku & kategori
- Peminjaman dan pengembalian
- Pembuatan laporan kegiatan

### 👨‍💼 Staff
- Input & edit data buku
- Proses peminjaman & pengembalian

### 👨‍🎓 Member
- Melihat katalog buku
- Melakukan peminjaman
- Melihat riwayat peminjaman

---

## 🏗️ Struktur Proyek

matchagipi/
├── src/
│ ├── model/
│ ├── controller/
│ ├── view/
│ └── main/
├── database/
├── matchagipi.sql
└── README.md

yaml
Copy
Edit

---

## ⚙️ Cara Menjalankan

1. Install **JDK 11+** dan **NetBeans IDE**
2. Import `matchagipi.sql` ke MySQL
3. Sesuaikan konfigurasi `DatabaseConnection.java`
4. Jalankan melalui `Matchagipi.java`

---

## 🛠️ Dependensi Tambahan

- ✅ `FlatLaf` – Tampilan modern
- ✅ `JSVG` – Render ikon SVG

---

## 📈 Saran Pengembangan

- Notifikasi keterlambatan via email
- Fitur denda otomatis
- QR Code untuk peminjaman
- Ekspor laporan ke PDF

---

## 📄 Lisensi

Proyek ini bersifat **open-source** dan bebas digunakan untuk pembelajaran dan pengembangan lebih lanjut.

---

> Dibuat dengan 💻 oleh Tim **Matchagipi** — Sistem perpustakaan modern untuk semua.
