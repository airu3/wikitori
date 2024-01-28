package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ScoreDao {
    final String URL = "jdbc:mysql://localhost:3306/shiritori";
    final String USER = "root";
    final String PASSWORD = "";

    public void registerUser(String name) {
        String sql = "INSERT INTO user (name) VALUES (?)";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registerScore(int userId, int score) {
        String sql = "INSERT INTO score (user_id, score) VALUES (?, ?)";
    
        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
    
            pstmt.setInt(1, userId);
            pstmt.setInt(2, score);
            pstmt.executeUpdate();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}