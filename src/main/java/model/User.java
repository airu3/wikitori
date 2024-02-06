package model;

/**
 * ユーザー情報を格納するクラス
 * 
 */
public class User {
	private int id;
	private String name;
	private int score;

	/**
	 * スコアを取得
	 * 
	 * @return score スコア
	 */
	public int getScore() {
		return score;
	}

	/**
	 * スコアを設定
	 * 
	 * @param score スコア
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * ユーザーIDを取得
	 * 
	 * @return id ユーザーID
	 */
	public int getId() {
		return id;
	}

	/**
	 * ユーザーIDを設定
	 * 
	 * @param id ユーザーID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * ユーザー名を取得
	 * 
	 * @return name ユーザー名
	 */
	public String getName() {
		return name;
	}

	/**
	 * ユーザー名を設定
	 * 
	 * @param name ユーザー名
	 */
	public void setName(String name) {
		this.name = name;
	}
}
