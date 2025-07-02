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

```text
matchagipi/
├── src/
│   ├── matchagipi/
│   │   ├── Admin.java
│   │   ├── AdminBuku.java
│   │   ├── AdminKategori.java
│   │   ├── AdminPeminjaman.java
│   │   ├── AdminPengembalian.java
│   │   ├── AdminUser.java
│   │   ├── DatabaseConnection.java
│   │   ├── FormLogin.java
│   │   ├── FormRegister.java
│   │   ├── HalamanUtama.java
│   │   ├── Matchagipi.java
│   │   ├── Member.java
│   │   ├── MemberPeminjaman.java
│   │   ├── MemberRiwayatPeminjaman.java
│   │   ├── Staff.java
│   │   ├── StaffBuku.java
│   │   ├── StaffKategori.java
│   │   ├── StaffPeminjaman.java
│   │   └── StaffPengembalian.java
│   ├── matchagipi.dao/
│   ├── matchagipi.icon/
│   └── matchagipi.model/
├── Libraries/
│   ├── flatlaf-3.2.1.jar
│   ├── flatlaf-extras-3.2.5.jar
│   ├── datechooser.jar
│   ├── mysql-connector-j-9.0.0.jar
│   ├── jsvg-1.3.0.jar
│   └── jcalendar-1.4.jar
├── JDK 24 (Default)
└── README.md (optional)
```

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
