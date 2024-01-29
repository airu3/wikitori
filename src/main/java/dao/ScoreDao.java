package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScoreDao extends BaseDao {
	private String URL = "jdbc:mysql://localhost:3306/shiritori";
	private String USER = "root";
	private String PASSWORD = "";

	/**
	 * コンストラクタ
	 */
	public ScoreDao() {
		super();
	}

	/**
	 * データベースに接続できるかどうかを返す
	 * 
	 * @return 接続できる場合はtrue、できない場合はfalse
	 */
	public boolean canConnectToDatabase() {
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)) {
			return true;
		} catch (SQLException e) {
			System.out.println("Failed to connect to database");
			e.printStackTrace();
			return false;
		} finally {
			try {
				this.disConnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ユーザーを登録する
	 * 
	 * @param name ユーザー名
	 */
	public void registerUser(String name) {
		String sql = "INSERT INTO user (name) VALUES (?)";

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, name);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				this.disConnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * スコアを登録する
	 * 
	 * @param userId ユーザーID
	 * @param score  スコア
	 */
	public void registerScore(int userId, int score) {
		String sql = "INSERT INTO score (user_id, score) VALUES (?, ?)";

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, userId);
			pstmt.setInt(2, score);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				this.disConnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ユーザーが存在するかどうかを返す
	 * 
	 * @param name ユーザー名
	 * @return 存在する場合はtrue、存在しない場合はfalse
	 */
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
		} finally {
			try {
				this.disConnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return false;
	}

	/**
	 * ユーザーのスコアを返す
	 * 
	 * @param userId ユーザーID
	 * @return スコア
	 */
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
		} finally {
			try {
				this.disConnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return -1;
	}

	/**
	 * スコアを設定する
	 * 
	 * @param userId ユーザーID
	 * @param score  スコア
	 */
	public void setScore(int userId, int score) {
		String sql = "INSERT INTO score (user_id, score) VALUES (?, ?)";

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, userId);
			pstmt.setInt(2, score);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				this.disConnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * スコアを更新する
	 * 
	 * @param userId
	 * @param score  スコア
	 */
	public void updateScore(int userId, int score) {
		String sql = "UPDATE score SET score = ? WHERE user_id = ?";

		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, score);
			pstmt.setInt(2, userId);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				this.disConnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ユーザーIDを返す
	 * 
	 * @param userName ユーザー名
	 * @return ユーザーID
	 */
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
		} finally {
			try {
				this.disConnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return -1;
	}
}