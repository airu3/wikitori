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

	/**
	 * DBに接続する
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	protected void connect() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(URL, USER, PASSWORD);
	}

	/**
	 * DBとの接続を切断する
	 * 
	 * @throws SQLException
	 */
	protected void disConnect() throws SQLException {
		if (con != null) {
			con.close();
		}
	}

}