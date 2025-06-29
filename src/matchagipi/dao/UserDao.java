/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matchagipi.dao;

import matchagipi.model.User;
import matchagipi.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author FERDY
 */
public class UserDao {
    public List<User> getAllUsers() {
    List<User> listUser = new ArrayList<>();
    String sql = "SELECT * FROM users"; // tanpa filter role

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            User user = new User(
                rs.getInt("id_user"),
                rs.getString("nama_lengkap"),
                rs.getString("username"),
                rs.getString("password"), // sesuaikan kalau perlu
                rs.getString("role")
            );
            listUser.add(user);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return listUser;
}

    
    public List<User> getAnggotaUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'anggota'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                    rs.getInt("id_user"),
                    rs.getString("nama_lengkap"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
    
    public boolean insertUser(User user) {
    String sql = "INSERT INTO users (nama_lengkap, username, password, role) VALUES (?, ?, ?, ?)";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, user.getNamaLengkap());
        stmt.setString(2, user.getUsername());
        stmt.setString(3, user.getPassword());
        stmt.setString(4, user.getRole());

        int rowsInserted = stmt.executeUpdate();
        return rowsInserted > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
    public boolean deleteUser(int idUser) {
    String sql = "DELETE FROM users WHERE id_user = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idUser);

        int rowsDeleted = stmt.executeUpdate();
        return rowsDeleted > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    public boolean updateUser(User user) {
    String sql = "UPDATE users SET nama_lengkap = ?, username = ?, password = ?, role = ? WHERE id_user = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, user.getNamaLengkap());
        stmt.setString(2, user.getUsername());
        stmt.setString(3, user.getPassword());
        stmt.setString(4, user.getRole());
        stmt.setInt(5, user.getIdUser());

        int rowsUpdated = stmt.executeUpdate();
        return rowsUpdated > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
    public User getUserById(int idUser) {
    User user = null;

    String query = "SELECT * FROM users WHERE id_user = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, idUser);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String namaLengkap = rs.getString("nama_lengkap");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String role = rs.getString("role");

            user = new User(idUser, namaLengkap, username, password, role);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return user;
}

    public boolean isUsernameExist(String username) {
    String query = "SELECT COUNT(*) FROM users WHERE username = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}
    
}
