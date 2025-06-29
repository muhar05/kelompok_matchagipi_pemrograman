/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matchagipi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import matchagipi.dao.BukuDao;
import matchagipi.dao.KategoriDao;
import matchagipi.model.Buku;
import matchagipi.model.Kategori;
/**
 *
 * @author FERDY
 */
public class AdminBuku extends javax.swing.JFrame {

    /**
     * Creates new form AdminBuku
     */
    public AdminBuku() {
        initComponents();
        setLocationRelativeTo(null);
        loadKategoriToComboBox(); 
        inputIdBuku.setEnabled(false);    
        loadDataBuku();
        ((JTextField) inputTahun.getDateEditor().getUiComponent()).setEditable(false);
        tableBuku.addMouseListener(new java.awt.event.MouseAdapter() {
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        tableBukuMouseClicked(evt);
    }
});

    }
    
    private KategoriDao kategoriDao = new KategoriDao(); // pastikan sudah diinisialisasi
    
    private void loadKategoriToComboBox() {
        selectedKategori.removeAllItems();

        List<Kategori> daftarKategori = kategoriDao.getAllKategori();

        for (Kategori kategori : daftarKategori) {
            selectedKategori.addItem(kategori);  // add objek kategori langsung
        }
}
    
    // Fungsi contoh untuk load data buku dan update JTable
    private void loadDataBuku() {
          KategoriDao kategoriDao = new KategoriDao();
        List<Kategori> kategoriList = kategoriDao.getAllKategori();
        Map<Integer, String> mapKategori = new HashMap<>();
        for (Kategori k : kategoriList) {
            mapKategori.put(k.getIdKategori(), k.getNamaKategori());
        }
        
        BukuDao bukuDao = new BukuDao();
        List<Buku> listBuku = bukuDao.getAllBuku();

        // Buat model tabel baru sesuai data listBuku (contoh sederhananya)
        String[] columnNames = {"ID", "Judul", "Penulis", "Penerbit", "Tahun", "ISBN", "Kategori", "Stok"};
        Object[][] data = new Object[listBuku.size()][columnNames.length];

        for (int i = 0; i < listBuku.size(); i++) {
            Buku b = listBuku.get(i);
            data[i][0] = b.getIdBuku();
            data[i][1] = b.getJudulBuku();
            data[i][2] = b.getPenulis();
            data[i][3] = b.getPenerbit();
            data[i][4] = b.getTahunTerbit();
            data[i][5] = b.getIsbn();
            data[i][6] = mapKategori.getOrDefault(b.getIdKategori(), "Unknown");
            data[i][7] = b.getStok();
        }

        tableBuku.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }
    
    private void tableBukuMouseClicked(java.awt.event.MouseEvent evt) {                                     
    int selectedRow = tableBuku.getSelectedRow();
    if (selectedRow >= 0) {
        // Ambil data dari tabel
        String idBuku = tableBuku.getValueAt(selectedRow, 0).toString();    // kolom ID buku
        String judul = tableBuku.getValueAt(selectedRow, 1).toString();
        String penulis = tableBuku.getValueAt(selectedRow, 2).toString();
        String penerbit = tableBuku.getValueAt(selectedRow, 3).toString();
        String tahunTerbit = tableBuku.getValueAt(selectedRow, 4).toString();
        String isbn = tableBuku.getValueAt(selectedRow, 5).toString();
        String kategori = tableBuku.getValueAt(selectedRow, 6).toString();  // misal nama kategori
        String stok = tableBuku.getValueAt(selectedRow, 7).toString();

        // Set ke form input
        inputIdBuku.setText(idBuku);
        inputJudulBuku.setText(judul);
        inputPenulis.setText(penulis);
        inputPenerbit.setText(penerbit);
        // untuk tahun terbit biasanya Date chooser, set dari string tahun
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            java.util.Date date = sdf.parse(tahunTerbit);
            inputTahun.setDate(date);
        } catch (ParseException e) {
            inputTahun.setDate(null);
        }
        inputIsbn.setText(isbn);
        
        // Set kategori - misal JComboBox kategori berisi objek Kategori, kamu perlu cari index kategori yang sesuai
        for (int i = 0; i < selectedKategori.getItemCount(); i++) {
            if (selectedKategori.getItemAt(i).toString().equals(kategori)) {
                selectedKategori.setSelectedIndex(i);
                break;
            }
        }

        inputStok.setText(stok);
    }
}
    
    // Fungsi untuk mengosongkan form input
    private void clearForm() {
        inputJudulBuku.setText("");
        inputPenulis.setText("");
        inputPenerbit.setText("");
        inputIsbn.setText("");
        inputTahun.setDate(null);
        selectedKategori.setSelectedIndex(0);
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
        btnBack = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        inputJudulBuku = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        inputPenulis = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        inputPenerbit = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        inputIsbn = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBuku = new javax.swing.JTable();
        btnTambahBuku = new javax.swing.JButton();
        btnEditBuku = new javax.swing.JButton();
        btnHapusBuku = new javax.swing.JButton();
        selectedKategori = new javax.swing.JComboBox();
        inputTahun = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        inputIdBuku = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        inputStok = new javax.swing.JTextField();
        btnSimpanBuku = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(132, 174, 146));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Admin Buku");

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
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
                .addComponent(btnBack)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnBack)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(185, 212, 170));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setText("Judul Buku :");

        inputJudulBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputJudulBukuActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("Penulis");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setText("Penerbit :");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setText("Tahun :");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setText("ISBN :");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel7.setText("Kategori :");

        tableBuku.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tableBuku);

        btnTambahBuku.setBackground(new java.awt.Color(250, 255, 202));
        btnTambahBuku.setText("Tambah");
        btnTambahBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahBukuActionPerformed(evt);
            }
        });

        btnEditBuku.setBackground(new java.awt.Color(250, 255, 202));
        btnEditBuku.setText("Edit");
        btnEditBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditBukuActionPerformed(evt);
            }
        });

        btnHapusBuku.setBackground(new java.awt.Color(250, 255, 202));
        btnHapusBuku.setText("Hapus");
        btnHapusBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusBukuActionPerformed(evt);
            }
        });

        selectedKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectedKategoriActionPerformed(evt);
            }
        });

        inputTahun.setDateFormatString("yyyy");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel8.setText("ID BUKU :");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setText("Stock :");

        btnSimpanBuku.setBackground(new java.awt.Color(250, 255, 202));
        btnSimpanBuku.setText("Simpan");
        btnSimpanBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanBukuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputStok))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputPenulis, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputJudulBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputTahun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(selectedKategori, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputIdBuku)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnTambahBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnEditBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnHapusBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSimpanBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(inputIdBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnTambahBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(btnEditBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapusBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSimpanBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputJudulBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputPenulis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputPenerbit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputTahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(selectedKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditBukuActionPerformed
        int selectedRow = tableBuku.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris data yang ingin diedit!");
            return;
        }

        // Ambil data dari form input
        String idBuku = inputIdBuku.getText();
        String judul = inputJudulBuku.getText();
        String penulis = inputPenulis.getText();
        String penerbit = inputPenerbit.getText();
        String tahunTerbit = "";
        if (inputTahun.getDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            tahunTerbit = sdf.format(inputTahun.getDate());
        }
        String isbn = inputIsbn.getText();
        String kategori = selectedKategori.getSelectedItem().toString();
        String stok = inputStok.getText();

        // Validasi sederhana (boleh kamu kembangkan)
        if (judul.isEmpty() || penulis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Judul dan Penulis harus diisi!");
            return;
        }

        // Update data di model tabel
        DefaultTableModel model = (DefaultTableModel) tableBuku.getModel();
        model.setValueAt(idBuku, selectedRow, 0);
        model.setValueAt(judul, selectedRow, 1);
        model.setValueAt(penulis, selectedRow, 2);
        model.setValueAt(penerbit, selectedRow, 3);
        model.setValueAt(tahunTerbit, selectedRow, 4);
        model.setValueAt(isbn, selectedRow, 5);
        model.setValueAt(kategori, selectedRow, 6);
        model.setValueAt(stok, selectedRow, 7);

        JOptionPane.showMessageDialog(this, "Data buku berhasil diperbarui!");
    }//GEN-LAST:event_btnEditBukuActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        this.dispose(); // Tutup form saat ini
        // Ganti "username" dan "role" di bawah sesuai data yang kamu miliki
        Admin admin = new Admin("admin01", "Admin");
        admin.setVisible(true);
        admin.setLocationRelativeTo(null); // Tampilkan di tengah layar
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnTambahBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahBukuActionPerformed
          // Kosongkan semua input
        inputIdBuku.setText("");
        inputJudulBuku.setText("");
        inputPenulis.setText("");
        inputPenerbit.setText("");
        inputTahun.setDate(null);
        inputIsbn.setText("");
        inputStok.setText("");
        selectedKategori.setSelectedIndex(0);

        // Deselect baris pada tabel
        tableBuku.clearSelection();
    }//GEN-LAST:event_btnTambahBukuActionPerformed

    private void btnHapusBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusBukuActionPerformed
       int selectedRow = tableBuku.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih baris data yang ingin dihapus!");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data ini?", 
                                                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        // Ambil ID dari baris yang dipilih
        DefaultTableModel model = (DefaultTableModel) tableBuku.getModel();
        int idBuku = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());

        // Hapus dari database
        BukuDao bukuDao = new BukuDao();
        boolean berhasil = bukuDao.deleteBuku(idBuku);

        if (berhasil) {
            model.removeRow(selectedRow); // Baru hapus dari tampilan tabel
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menghapus data dari database!");
        }
    }
    }//GEN-LAST:event_btnHapusBukuActionPerformed

    private void selectedKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectedKategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectedKategoriActionPerformed

    private void btnSimpanBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanBukuActionPerformed
          // Ambil input dari form
        String judul = inputJudulBuku.getText();
        String penulis = inputPenulis.getText();
        String penerbit = inputPenerbit.getText();

        // Format tahun dari JDateChooser ke String (yyyy)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String tahun = "";
        if (inputTahun.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Format tahun tidak valid!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (inputTahun.getDate() != null) {
            tahun = sdf.format(inputTahun.getDate());
        }

        String isbn = inputIsbn.getText();
        Kategori selected = (Kategori) selectedKategori.getSelectedItem();
    if (selected == null) {
        JOptionPane.showMessageDialog(this, "Pilih kategori terlebih dahulu.");
        return;
    }
    int idKategori = selected.getIdKategori();

        // Ambil dan parsing input stok
        int stok = 1; // default
        try {
            stok = Integer.parseInt(inputStok.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stok harus berupa angka!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validasi minimal
        if (judul.isEmpty() || penulis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Judul dan Penulis harus diisi!");
            return;
        }

        // Buat objek Buku
        Buku bukuBaru = new Buku(0, judul, penulis, penerbit, tahun, isbn, idKategori, stok);

        // Simpan ke database menggunakan BukuDao
        BukuDao bukuDao = new BukuDao();
        boolean berhasil = bukuDao.insertBuku(bukuBaru);

        if (berhasil) {
            JOptionPane.showMessageDialog(this, "Buku berhasil ditambahkan!");
            loadDataBuku();
            // Bersihkan input setelah simpan
            btnTambahBukuActionPerformed(null);
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan buku!");
        }
    }//GEN-LAST:event_btnSimpanBukuActionPerformed

    private void inputJudulBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputJudulBukuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputJudulBukuActionPerformed

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
            java.util.logging.Logger.getLogger(AdminBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminBuku.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminBuku().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnEditBuku;
    private javax.swing.JButton btnHapusBuku;
    private javax.swing.JButton btnSimpanBuku;
    private javax.swing.JButton btnTambahBuku;
    private javax.swing.JTextField inputIdBuku;
    private javax.swing.JTextField inputIsbn;
    private javax.swing.JTextField inputJudulBuku;
    private javax.swing.JTextField inputPenerbit;
    private javax.swing.JTextField inputPenulis;
    private javax.swing.JTextField inputStok;
    private com.toedter.calendar.JDateChooser inputTahun;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox selectedKategori;
    private javax.swing.JTable tableBuku;
    // End of variables declaration//GEN-END:variables
}
