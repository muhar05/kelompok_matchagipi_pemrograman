/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matchagipi.model;

/**
 *
 * @author FERDY
 */
public class User {
    private int idUser;
    private String namaLengkap;
    private String username;
    private String password;
    private String role;

    // Constructor
    public User(int idUser, String namaLengkap, String username, String password, String role) {
        this.idUser = idUser;
        this.namaLengkap = namaLengkap;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter dan Setter
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

