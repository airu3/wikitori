package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScoreDao {
	private static final String URL = "jdbc:mysql://localhost:3306/test";
	private static final String USER = "root";
	private static final String PASSWORD = "";

	// コネクション
	protected Connection con = null;

	// DB接続する
	protected void connect() throws SQLException, ClassNotFoundException {
		con = DriverManager.getConnection(URL, USER, PASSWORD);
	}

	// DB切断する
	protected void disConnect() throws SQLException {
		if (con != null) {
			con.close();
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