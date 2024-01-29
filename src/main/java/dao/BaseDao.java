package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * BaseDaoクラス
 * 各DAOクラスが継承するクラス
 */
public class BaseDao {
	protected String URL = "jdbc:mysql://localhost:3306";
	protected String USER = "root";
	protected String PASSWORD = "";

	// コネクション
	protected Connection con = null;

	/**
	 * コンストラクタ
	 */
	protected BaseDao() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found");
			e.printStackTrace();
		}
	}

	// DB接続する
	protected void connect() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(URL, USER, PASSWORD);
	}

	// DB切断する
	protected void disConnect() throws SQLException {
		if (con != null) {
			con.close();
		}
	}

}