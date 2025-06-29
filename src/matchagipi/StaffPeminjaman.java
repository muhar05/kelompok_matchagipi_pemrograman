/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matchagipi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import matchagipi.dao.BukuDao;
import matchagipi.dao.PeminjamanDao;
import matchagipi.dao.UserDao;
import matchagipi.model.Buku;
import matchagipi.model.User;

/**
 *
 * @author FERDY
 */
public class StaffPeminjaman extends javax.swing.JFrame {

    /**
     * Creates new form StaffPeminjaman
     */
    public StaffPeminjaman() {
        initComponents();
        setLocationRelativeTo(null);
        loadDataPeminjaman();
        loadAnggotaToComboBox();
        loadBukuToTable();
        populateInputsFromSelectedPeminjaman();
        tablePeminjaman.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            populateInputsFromSelectedPeminjaman();
        }
    }
});
    }
    
    private void populateInputsFromSelectedPeminjaman() {
    int selectedRow = tablePeminjaman.getSelectedRow();

    if (selectedRow != -1) {
        try {
            // Ambil data dari tabel
            String idUser = tablePeminjaman.getValueAt(selectedRow, 1).toString();        // kolom ID user
            String namaUser = tablePeminjaman.getValueAt(selectedRow, 2).toString();      // kolom Nama Peminjam
            String judulBuku = tablePeminjaman.getValueAt(selectedRow, 3).toString();     // kolom Judul Buku
            java.sql.Date tanggalPinjam = (java.sql.Date) tablePeminjaman.getValueAt(selectedRow, 4);
            java.sql.Date tanggalKembali = (java.sql.Date) tablePeminjaman.getValueAt(selectedRow, 5);
            String status = tablePeminjaman.getValueAt(selectedRow, 6).toString();        // kolom Status

            // Set combo box anggota
            for (int i = 0; i < selectedMember.getItemCount(); i++) {
                String item = (String) selectedMember.getItemAt(i);
                if (item.startsWith(idUser + " -")) {
                    selectedMember.setSelectedIndex(i);
                    break;
                }
            }

            // Cari judul buku di tableSelectedBuku lalu set baris terpilih
            for (int i = 0; i < tableSelectedBuku.getRowCount(); i++) {
                Object judul = tableSelectedBuku.getValueAt(i, 1); // Kolom ke-1 adalah Judul
                if (judul != null && judul.equals(judulBuku)) {
                    tableSelectedBuku.setRowSelectionInterval(i, i);
                    break;
                }
            }

            // Set tanggal dan status
            inputTanggaPinjam.setDate(new java.util.Date(tanggalPinjam.getTime()));
            inputTanggalKembali.setDate(new java.util.Date(tanggalKembali.getTime()));
            inputStatus.setSelectedItem(status);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat data ke input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
    
    private void loadBukuToTable() {
    BukuDao bukuDao = new BukuDao();
    List<Buku> daftarBuku = bukuDao.getAllBuku();

    String[] kolom = {"ID", "Judul", "Penulis", "Penerbit", "Tahun Terbit", "ISBN", "ID Kategori", "Stok"};
    DefaultTableModel model = new DefaultTableModel(kolom, 0);

    for (Buku buku : daftarBuku) {
        Object[] row = {
            buku.getIdBuku(),
            buku.getJudulBuku(),
            buku.getPenulis(),
            buku.getPenerbit(),
            buku.getTahunTerbit(),
            buku.getIsbn(),
            buku.getIdKategori(),
            buku.getStok()
        };
        model.addRow(row);
    }

    tableSelectedBuku.setModel(model);
}
    
    private void loadAnggotaToComboBox() {
    UserDao userDao = new UserDao();
    List<User> anggotaList = userDao.getAnggotaUsers();

    selectedMember.removeAllItems(); // Kosongkan dulu
    for (User user : anggotaList) {
        selectedMember.addItem(user.getIdUser() + " - " + user.getNamaLengkap()); // Contoh: "3 - Budi"
    }
}
    
    private String getSelectedUserId() {
    String selected = (String) selectedMember.getSelectedItem();
    if (selected != null && selected.contains(" - ")) {
        return selected.split(" - ")[0]; // Ambil ID-nya saja
    }
    return null;
}
    private String generateNewIdPengembalian() {
    String prefix = "PGM";
    String newId = prefix + "0001"; // Default jika belum ada data

    try (Connection conn = DatabaseConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT id_pengembalian FROM pengembalian ORDER BY id_pengembalian DESC LIMIT 1")) {

        if (rs.next()) {
            String lastId = rs.getString("id_pengembalian"); // Misalnya: PGM0032
            int number = Integer.parseInt(lastId.substring(prefix.length())); // Ambil angka: 32
            number++; // Tambah 1
            newId = String.format("%s%04d", prefix, number); // Format jadi PGM0033
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal membuat ID pengembalian baru.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    return newId;
}
    
    public String generateNewIdPeminjaman() {
    String newId = null;
    String sql = "SELECT id_peminjaman FROM peminjaman ORDER BY id_peminjaman DESC LIMIT 1";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            String lastId = rs.getString("id_peminjaman"); // contoh: "P001"
            int num = Integer.parseInt(lastId.substring(1)) + 1;
            newId = String.format("P%03d", num);
        } else {
            newId = "P001"; // default awal
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return newId;
}
    
    private void loadDataPeminjaman() {
    DefaultTableModel model = new DefaultTableModel();
    model.setColumnIdentifiers(new String[]{"ID", "ID User", "Nama Peminjam", "Judul Buku", "Tanggal Pinjam", "Tanggal Kembali", "Status"});

    try {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT p.id_peminjaman, p.id_user, u.nama_lengkap, b.judul_buku, p.tanggal_pinjam, p.tanggal_kembali, p.status " +
                     "FROM peminjaman p " +
                     "JOIN users u ON p.id_user = u.id_user " +
                     "JOIN buku b ON p.id_buku = b.id_buku";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("id_peminjaman"),
                rs.getString("id_user"),
                rs.getString("nama_lengkap"),
                rs.getString("judul_buku"),
                rs.getDate("tanggal_pinjam"),
                rs.getDate("tanggal_kembali"),
                rs.getString("status")
            });
        }

        rs.close();
        ps.close();
        conn.close();

        // Inilah yang merender ke JTable
        tablePeminjaman.setModel(model);

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal memuat data peminjaman", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnEditPeminjaman = new javax.swing.JButton();
        selectedMember = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        inputStatus = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSelectedBuku = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablePeminjaman = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        inputTanggaPinjam = new com.toedter.calendar.JDateChooser();
        inputTanggalKembali = new com.toedter.calendar.JDateChooser();
        btnTambahPeminjaman = new javax.swing.JButton();
        btnHapusPeminjaman = new javax.swing.JButton();
        btnSimpanPeminjaman = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(132, 174, 146));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Staff Peminjaman Form");

        jButton1.setText("back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(185, 212, 170));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setText("Member");

        btnEditPeminjaman.setText("Edit");
        btnEditPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditPeminjamanActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("Tanggal Pinjam");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setText("Tanggal Kembali");

        inputStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "dipinjam", "dikembalikan" }));
        inputStatus.setSelectedIndex(-1);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setText("Tabel data Peminjam");

        tableSelectedBuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableSelectedBuku);

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setText("Status");

        tablePeminjaman.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tablePeminjaman);

        jLabel7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel7.setText("Tabel Buku");

        btnTambahPeminjaman.setText("Tambah");
        btnTambahPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahPeminjamanActionPerformed(evt);
            }
        });

        btnHapusPeminjaman.setText("Hapus");
        btnHapusPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusPeminjamanActionPerformed(evt);
            }
        });

        btnSimpanPeminjaman.setText("Simpan");
        btnSimpanPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanPeminjamanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(inputTanggaPinjam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(inputTanggalKembali, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(inputStatus, 0, 327, Short.MAX_VALUE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(selectedMember, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnEditPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnHapusPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnTambahPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnSimpanPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectedMember, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputTanggaPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputTanggalKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(inputStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpanPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambahPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditPeminjamanActionPerformed
        int selectedRow = tablePeminjaman.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih data peminjaman yang ingin diedit.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Ambil ID peminjaman dari baris yang dipilih
    String idPeminjaman = tablePeminjaman.getValueAt(selectedRow, 0).toString();

    // Ambil tanggal dari input komponen
    java.util.Date tanggalPinjam = inputTanggaPinjam.getDate();
    java.util.Date tanggalKembali = inputTanggalKembali.getDate();

    if (tanggalPinjam == null || tanggalKembali == null) {
        JOptionPane.showMessageDialog(this, "Tanggal pinjam dan kembali tidak boleh kosong.", "Validasi", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Konversi ke java.sql.Date
    java.sql.Date sqlTanggalPinjam = new java.sql.Date(tanggalPinjam.getTime());
    java.sql.Date sqlTanggalKembali = new java.sql.Date(tanggalKembali.getTime());

    // Update data di database
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(
             "UPDATE peminjaman SET tanggal_pinjam = ?, tanggal_kembali = ? WHERE id_peminjaman = ?")) {

        stmt.setDate(1, sqlTanggalPinjam);
        stmt.setDate(2, sqlTanggalKembali);
        stmt.setString(3, idPeminjaman);

        int rowsUpdated = stmt.executeUpdate();

        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(this, "Tanggal peminjaman berhasil diperbarui.");
            loadDataPeminjaman(); // refresh tabel
        } else {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui tanggal peminjaman.", "Gagal", JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memperbarui data.", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnEditPeminjamanActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose(); // Menutup form saat ini
        Staff staff = new Staff("admin01", "Admin");
        staff.setVisible(true);
        staff.setLocationRelativeTo(null); // Agar muncul di tengah layar
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnTambahPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahPeminjamanActionPerformed
         // Reset semua input
        selectedMember.setSelectedIndex(-1); // Kosongkan pilihan anggota
        tableSelectedBuku.clearSelection();  // Kosongkan seleksi tabel buku
        inputTanggaPinjam.setDate(null);     // Kosongkan tanggal pinjam
        inputTanggalKembali.setDate(null);   // Kosongkan tanggal kembali
        inputStatus.setSelectedIndex(-1);    // Kosongkan status

        JOptionPane.showMessageDialog(this, "Silakan isi data peminjaman baru.");
    }//GEN-LAST:event_btnTambahPeminjamanActionPerformed

    private void btnHapusPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusPeminjamanActionPerformed
         int selectedRow = tablePeminjaman.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih data peminjaman yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data peminjaman ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        String idPeminjaman = tablePeminjaman.getValueAt(selectedRow, 0).toString(); // Ambil ID peminjaman dari kolom pertama

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM peminjaman WHERE id_peminjaman = ?")) {

            stmt.setString(1, idPeminjaman);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Data peminjaman berhasil dihapus.");
                loadDataPeminjaman(); // Refresh tabel setelah penghapusan
            } else {
                JOptionPane.showMessageDialog(this, "Data peminjaman gagal dihapus.", "Gagal", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menghapus data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_btnHapusPeminjamanActionPerformed

    private void btnSimpanPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanPeminjamanActionPerformed
        try {
    // Ambil ID user dari combo box selectedMember
    String selectedUser = (String) selectedMember.getSelectedItem();
    if (selectedUser == null || !selectedUser.contains(" - ")) {
        JOptionPane.showMessageDialog(this, "Pilih anggota peminjam dulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
    int idUser = Integer.parseInt(selectedUser.split(" - ")[0]);

    // Ambil buku yang dipilih di tableSelectedBuku (baris aktif)
    int selectedRow = tableSelectedBuku.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih buku yang ingin dipinjam dari tabel!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
    int idBuku = (int) tableSelectedBuku.getValueAt(selectedRow, 0); // Kolom ID buku

    // Ambil tanggal pinjam dan kembali dari komponen datechooser
    java.util.Date tanggalPinjam = inputTanggaPinjam.getDate();
    java.util.Date tanggalKembali = inputTanggalKembali.getDate();

    if (tanggalPinjam == null || tanggalKembali == null) {
        JOptionPane.showMessageDialog(this, "Isi tanggal pinjam dan tanggal kembali!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (tanggalKembali.before(tanggalPinjam)) {
        JOptionPane.showMessageDialog(this, "Tanggal kembali harus setelah tanggal pinjam!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Ambil status dari combo box inputStatus
    String status = (String) inputStatus.getSelectedItem();
    if (status == null || status.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Pilih status peminjaman!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Simpan ke database
    Connection conn = DatabaseConnection.getConnection();
    String newId = generateNewIdPeminjaman();

    // Simpan ke tabel peminjaman
    String sql = "INSERT INTO peminjaman (id_peminjaman, id_user, id_buku, tanggal_pinjam, tanggal_kembali, status) VALUES (?, ?, ?, ?, ?, ?)";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, newId);
    ps.setInt(2, idUser);
    ps.setInt(3, idBuku);
    ps.setDate(4, new java.sql.Date(tanggalPinjam.getTime()));
    ps.setDate(5, new java.sql.Date(tanggalKembali.getTime()));
    ps.setString(6, status);

    int rowsInserted = ps.executeUpdate();

    if (rowsInserted > 0) {
        // Simpan ke tabel pengembalian juga
        String newIdPengembalian = generateNewIdPengembalian(); // Buat ID pengembalian unik
        String sqlPengembalian = "INSERT INTO pengembalian (id_pengembalian, id_peminjaman, tanggal_kembali) VALUES (?, ?, ?)";
        PreparedStatement psPengembalian = conn.prepareStatement(sqlPengembalian);
        psPengembalian.setString(1, newIdPengembalian);
        psPengembalian.setString(2, newId); // id_peminjaman yang barusan dibuat
        psPengembalian.setDate(3, new java.sql.Date(tanggalKembali.getTime()));

        int rowsInsertedPengembalian = psPengembalian.executeUpdate();
        psPengembalian.close();

        if (rowsInsertedPengembalian > 0) {
            JOptionPane.showMessageDialog(this, "Data peminjaman dan pengembalian berhasil ditambahkan!");
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan data ke tabel pengembalian.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        loadDataPeminjaman(); // Refresh tabel
    } else {
        JOptionPane.showMessageDialog(this, "Gagal menambahkan data peminjaman.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    ps.close();
    conn.close();

} catch (SQLException ex) {
    ex.printStackTrace();
    JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan data.", "Error", JOptionPane.ERROR_MESSAGE);
}
    }//GEN-LAST:event_btnSimpanPeminjamanActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StaffPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StaffPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StaffPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StaffPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StaffPeminjaman().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditPeminjaman;
    private javax.swing.JButton btnHapusPeminjaman;
    private javax.swing.JButton btnSimpanPeminjaman;
    private javax.swing.JButton btnTambahPeminjaman;
    private javax.swing.JComboBox inputStatus;
    private com.toedter.calendar.JDateChooser inputTanggaPinjam;
    private com.toedter.calendar.JDateChooser inputTanggalKembali;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox selectedMember;
    private javax.swing.JTable tablePeminjaman;
    private javax.swing.JTable tableSelectedBuku;
    // End of variables declaration//GEN-END:variables
}
