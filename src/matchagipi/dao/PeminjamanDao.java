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
import matchagipi.DatabaseConnection;
/**
 *
 * @author FERDY
 */
public class PeminjamanDao {
   // Total semua peminjaman
    public int getJumlahPeminjaman() {
        int count = 0;
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT COUNT(*) FROM peminjaman";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    // Jumlah buku yang sedang dipinjam (status = 'dipinjam')
    public int getJumlahBukuDipinjam() {
    int count = 0;
    try {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT COUNT(*) FROM peminjaman";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);
        }
        rs.close();
        ps.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return count;
}

    // Jumlah buku yang sudah dikembalikan (status = 'dikembalikan')
    public int getJumlahBukuDikembalikan() {
    int count = 0;
    try {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT COUNT(*) FROM peminjaman WHERE status = 'dikembalikan'";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            count = rs.getInt(1);
        }
        rs.close();
        ps.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return count;
}
    
    // INSERT peminjaman
public boolean insertPeminjaman(String idPeminjaman, String idUser, String idBuku, java.sql.Date tanggalPinjam, java.sql.Date tanggalKembali, String status) {
    try {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "INSERT INTO peminjaman (id_peminjaman, id_user, id_buku, tanggal_pinjam, tanggal_kembali, status) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, idPeminjaman);
        ps.setString(2, idUser);
        ps.setString(3, idBuku);
        ps.setDate(4, tanggalPinjam);
        ps.setDate(5, tanggalKembali);
        ps.setString(6, status);

        int rows = ps.executeUpdate();
        ps.close();
        conn.close();
        return rows > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

// UPDATE peminjaman
public boolean updatePeminjaman(String idPeminjaman, String idUser, String idBuku, java.sql.Date tanggalPinjam, java.sql.Date tanggalKembali, String status) {
    try {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "UPDATE peminjaman SET id_user = ?, id_buku = ?, tanggal_pinjam = ?, tanggal_kembali = ?, status = ? WHERE id_peminjaman = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, idUser);
        ps.setString(2, idBuku);
        ps.setDate(3, tanggalPinjam);
        ps.setDate(4, tanggalKembali);
        ps.setString(5, status);
        ps.setString(6, idPeminjaman);

        int rows = ps.executeUpdate();
        ps.close();
        conn.close();
        return rows > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

// DELETE peminjaman
public boolean deletePeminjaman(String idPeminjaman) {
    try {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "DELETE FROM peminjaman WHERE id_peminjaman = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, idPeminjaman);

        int rows = ps.executeUpdate();
        ps.close();
        conn.close();
        return rows > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}
