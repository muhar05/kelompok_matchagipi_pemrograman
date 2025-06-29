/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matchagipi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import matchagipi.DatabaseConnection;
import matchagipi.model.Kategori;

/**
 *
 * @author FERDY
 */
public class KategoriDao {
      public List<Kategori> getAllKategori() {
        List<Kategori> kategoriList = new ArrayList<>();
        String sql = "SELECT * FROM kategori";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Kategori kategori = new Kategori(
                    rs.getInt("id_kategori"),
                    rs.getString("nama_kategori")
                );
                kategoriList.add(kategori);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kategoriList;
    }

    public boolean insertKategori(String namaKategori) {
        String sql = "INSERT INTO kategori (nama_kategori) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, namaKategori);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateKategori(int idKategori, String namaKategori) {
        String sql = "UPDATE kategori SET nama_kategori = ? WHERE id_kategori = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, namaKategori);
            stmt.setInt(2, idKategori);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteKategori(int idKategori) {
        String sql = "DELETE FROM kategori WHERE id_kategori = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idKategori);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
