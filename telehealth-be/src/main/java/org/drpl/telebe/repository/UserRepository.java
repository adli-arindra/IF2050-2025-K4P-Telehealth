package org.drpl.telebe.repository;

import org.drpl.telebe.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    // Metode untuk mendapatkan koneksi database.
    private Connection getConnection() throws SQLException {
        // Placeholder: nanti diganti dengan yang di database
        return null;
    }

    //menambahkan user ke database
    public void addUser(User user) {
        // SQL query hanya menggunakan kolom yang ada di model User
        String sql = "INSERT INTO User (id_user, name, email, alamat, tanggal_lahir) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getAlamat());
            // Mengubah java.util.Date menjadi java.sql.Date
            pstmt.setDate(5, new java.sql.Date(user.getTanggal_lahir().getTime()));

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error saat menambahkan user: " + e.getMessage());
        }
    }

    //mengambil data user berdasarkan id dari database
    public User getUserById(String userId) {
        String sql = "SELECT * FROM User WHERE id_user = ?";
        User user = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Mengambil data dari setiap kolom yang relevan
                String id = rs.getString("id_user");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String alamat = rs.getString("alamat");
                Date tglLahir = rs.getDate("tanggal_lahir");
            }

        } catch (SQLException e) {
            System.out.println("Error saat mengambil user by ID: " + e.getMessage());
        }
        return user;
    }

    //update data user di database
    public void updateUser(User user) {
        String sql = "UPDATE User SET name = ?, email = ?, alamat = ?, tanggal_lahir = ? WHERE id_user = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getAlamat());
            pstmt.setDate(4, new java.sql.Date(user.getTanggal_lahir().getTime()));
            pstmt.setString(5, user.getId()); // Untuk WHERE

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error saat memperbarui user: " + e.getMessage());
        }
    }


    //delete user dari database
    public void deleteUser(String userId) {
        String sql = "DELETE FROM User WHERE id_user = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error saat menghapus user: " + e.getMessage());
        }
    }
}