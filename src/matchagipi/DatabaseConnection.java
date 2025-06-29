/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matchagipi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FERDY
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/perpustakaan_digital"; // Ganti sesuai DB kamu
    private static final String USER = "root"; // Ganti jika bukan root
    private static final String PASSWORD = ""; // Ganti jika pakai password

    private static Connection connection = null;

    // Method untuk mendapatkan koneksi
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load driver
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Koneksi gagal: " + e.getMessage());
            return null;
        }
    }
}
