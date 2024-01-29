package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScoreDao {
	final String URL = "jdbc:mysql://localhost:3306/shiritori";
	final String USER = "root";
	final String PASSWORD = "";

	public ScoreDao() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found");
			e.printStackTrace();
		}
	}

	public boolean canConnectToDatabase() {
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
			return true;
		} catch (SQLException e) {
			System.out.println("Failed to connect to database");
			e.printStackTrace();
			return false;
		}
	}

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

	public boolean isUserExists(String name) {
		String sql = "SELECT * FROM user WHERE name = ?";

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public int getUserScore(int userId) {
		String sql = "SELECT score FROM score WHERE user_id = ?";

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("score");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	public void setScore(int userId, int score) {
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

	public void updateScore(int userId, int score) {
		String sql = "UPDATE score SET score = ? WHERE user_id = ?";

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, score);
			pstmt.setInt(2, userId);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getUserId(String userName) {
		String sql = "SELECT id FROM user WHERE name = ?";

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, userName);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}
}