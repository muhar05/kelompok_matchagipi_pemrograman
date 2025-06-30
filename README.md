📚 Matchagipi - Sistem Manajemen Perpustakaan
Matchagipi adalah aplikasi desktop berbasis Java untuk mengelola sistem perpustakaan, termasuk manajemen user, buku, peminjaman, pengembalian, dan pelaporan. Dibuat menggunakan Java Swing dengan NetBeans GUI Builder.

🏗️ Struktur Proyek
graphql
Copy
Edit
matchagipi/
├── src/
│   ├── model/            # Kelas model data (User, Buku, Peminjaman, dll)
│   ├── controller/       # Logika bisnis dan pengelolaan data
│   ├── view/             # GUI berbasis Java Swing
│   └── main/             # Kelas utama dan entry point aplikasi
├── database/             # File SQL dan konfigurasi database
├── README.md
└── matchagipi.sql        # Skrip database untuk inisialisasi sistem

👤 Fitur Utama Berdasarkan Role
🔐 Login & Autentikasi
Form login untuk semua pengguna

Registrasi user baru (via Admin atau Register Form)

🧑‍💼 Admin
Manajemen user

Manajemen buku dan kategori

Pengelolaan peminjaman & pengembalian

Pembuatan laporan kegiatan

👨‍💼 Staff
Pengelolaan data buku dan kategori

Peminjaman dan pengembalian buku oleh member

👨‍🎓 Member
Melihat buku

Melakukan peminjaman buku

Melihat riwayat peminjaman

⚙️ Cara Menjalankan
Kebutuhan Sistem

JDK 11 atau lebih tinggi

NetBeans IDE (disarankan)

MySQL (pastikan database tersedia)

Library tambahan seperti FlatLaf & JSVG jika digunakan

Langkah Setup

Clone repositori ini atau buka di NetBeans

Atur koneksi database di DatabaseConnection.java

Buat database dan tabel sesuai kebutuhan

Jalankan Matchagipi.java

🛠️ Dependensi Tambahan
FlatLaf – untuk tampilan UI modern

JSVG – untuk render ikon SVG

📝 Lisensi
Proyek ini bersifat open-source dan bebas digunakan untuk keperluan belajar dan pengembangan lebih lanjut.
