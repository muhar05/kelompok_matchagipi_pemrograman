📚 Matchagipi - Sistem Manajemen Perpustakaan Digital
Matchagipi adalah aplikasi desktop berbasis Java yang dirancang untuk membantu pengelolaan perpustakaan secara digital. Dibangun menggunakan Java Swing dan NetBeans GUI Builder, sistem ini mendukung berbagai fitur seperti manajemen pengguna, katalog buku, transaksi peminjaman-pengembalian, hingga laporan aktivitas perpustakaan.

✨ Fitur Utama Berdasarkan Role
🔐 Login & Autentikasi
Form login untuk semua pengguna (Admin, Staff, Member)
Sistem autentikasi dan validasi user
Registrasi user baru (oleh Admin atau melalui form)

🧑‍💼 Admin
Manajemen akun Staff & Member
CRUD buku dan kategori buku
Mengelola transaksi peminjaman & pengembalian

👨‍💼 Staff
CRUD data buku dan kategori
Memproses peminjaman & pengembalian buku
Melihat data pengguna & transaksi

👨‍🎓 Member
Melihat katalog buku
Melakukan peminjaman buku
Melihat status pinjaman & histori pengembalian

🏗️ Struktur Proyek
bash
Copy
Edit
matchagipi/
├── src/
│   ├── model/            # Model data seperti User, Buku, Peminjaman
│   ├── controller/       # Logika bisnis dan penghubung ke database
│   ├── view/             # Antarmuka pengguna dengan Java Swing
│   └── main/             # Entry point aplikasi
├── database/             # File SQL & konfigurasi awal database
├── README.md
└── matchagipi.sql        # Skrip untuk inisialisasi database
⚙️ Cara Menjalankan Aplikasi

📋 Kebutuhan Sistem
Java JDK 11 atau lebih baru
NetBeans IDE (disarankan versi terbaru)
MySQL Server aktif
Koneksi JDBC
FlatLaf dan JSVG (jika digunakan untuk tampilan modern)

🚀 Langkah Setup
Clone repositori ini atau download sebagai ZIP.
Buka proyek di NetBeans IDE.
Buat database MySQL baru dan import matchagipi.sql.
Konfigurasi file DatabaseConnection.java sesuai koneksi lokal Anda (user, password, db name).
Jalankan program melalui file Matchagipi.java.

🛠️ Dependensi Tambahan
FlatLaf – untuk UI modern & flat design
JSVG – untuk menampilkan ikon SVG
Tambahkan JAR dependensi ke dalam library proyek via NetBeans.

📈 Pengembangan Selanjutnya (Saran)
Modul notifikasi keterlambatan via email
Fitur denda otomatis
Integrasi QR Code untuk scan buku
Ekspor laporan ke PDF

📄 Lisensi
Proyek ini bersifat Open Source dan bebas digunakan untuk keperluan pembelajaran, penelitian, maupun pengembangan aplikasi skala kecil-menengah. Kontribusi sangat terbuka untuk perbaikan dan pengembangan fitur lebih lanjut.

🙌 Kontribusi
Ingin berkontribusi? Silakan:
Fork repositori ini
Buat perubahan di branch baru
Kirim pull request dengan deskripsi yang jelas

Dibuat dengan 💻 oleh tim pengembang Matchagipi
Aplikasi sederhana, bermanfaat nyata untuk pengelolaan perpustakaan modern
