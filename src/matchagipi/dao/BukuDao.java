/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matchagipi.dao;

import matchagipi.model.Buku;
import matchagipi.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author FERDY
 */
public class BukuDao {
     public List<Buku> getAllBuku() {
        List<Buku> bukuList = new ArrayList<>();
        String sql = "SELECT * FROM buku";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                bukuList.add(mapResultSetToBuku(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengambil data buku", e);
        }

        return bukuList;
    }

    public Buku getBukuById(int id) {
        String sql = "SELECT * FROM buku WHERE id_buku = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBuku(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengambil data buku berdasarkan ID", e);
        }

        return null;
    }

    public boolean insertBuku(Buku buku) {
        String sql = "INSERT INTO buku (judul_buku, penulis, penerbit, tahun_terbit, isbn, id_kategori, stok) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, buku.getJudulBuku());
            stmt.setString(2, buku.getPenulis());
            stmt.setString(3, buku.getPenerbit());
            stmt.setString(4, buku.getTahunTerbit());
            stmt.setString(5, buku.getIsbn());
            stmt.setInt(6, buku.getIdKategori());
            stmt.setInt(7, buku.getStok());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Gagal menambahkan buku", e);
        }
    }

    public boolean updateBuku(Buku buku) {
        String sql = "UPDATE buku SET judul_buku=?, penulis=?, penerbit=?, tahun_terbit=?, isbn=?, id_kategori=?, stok=? " +
                     "WHERE id_buku=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, buku.getJudulBuku());
            stmt.setString(2, buku.getPenulis());
            stmt.setString(3, buku.getPenerbit());
            stmt.setString(4, buku.getTahunTerbit());
            stmt.setString(5, buku.getIsbn());
            stmt.setInt(6, buku.getIdKategori());
            stmt.setInt(7, buku.getStok());
            stmt.setInt(8, buku.getIdBuku());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Gagal mengupdate buku", e);
        }
    }

    public boolean deleteBuku(int id) {
        String sql = "DELETE FROM buku WHERE id_buku = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Gagal menghapus buku", e);
        }
    }

    private Buku mapResultSetToBuku(ResultSet rs) throws SQLException {
        return new Buku(
            rs.getInt("id_buku"),
            rs.getString("judul_buku"),
            rs.getString("penulis"),
            rs.getString("penerbit"),
            rs.getString("tahun_terbit"),
            rs.getString("isbn"),
            rs.getInt("id_kategori"),
            rs.getInt("stok")
        );
    }
}
