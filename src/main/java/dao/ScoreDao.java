package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ScoreDao {
    final String URL = "jdbc:mysql://localhost:3306/shiritori";
    final String USER = "root";
    final String PASSWORD = "";

    public void registerScore(int score) {
        String sql = "INSERT INTO score_table (score) VALUES (?)";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, score);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //後で消す
}