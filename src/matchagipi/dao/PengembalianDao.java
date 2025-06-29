/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matchagipi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import matchagipi.DatabaseConnection;

/**
 *
 * @author FERDY
 */
public class PengembalianDao {
     public int getJumlahPengembalian() {
        int count = 0;
        try {
            Connection conn = DatabaseConnection.getConnection(); // pastikan koneksi aktif
            String sql = "SELECT COUNT(*) FROM pengembalian";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
     
      // ✅ Fungsi untuk mengambil semua data pengembalian
    public DefaultTableModel getAllPengembalian() {
        String[] kolom = {"ID Pengembalian", "ID Peminjaman", "Tanggal Kembali"};
        DefaultTableModel model = new DefaultTableModel(kolom, 0);

        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM pengembalian";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String idPengembalian = rs.getString("id_pengembalian");
                String idPeminjaman = rs.getString("id_peminjaman");
                java.sql.Date tanggalKembali = rs.getDate("tanggal_kembali");

                Object[] row = {idPengembalian, idPeminjaman, tanggalKembali};
                model.addRow(row);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return model;
    }
}
