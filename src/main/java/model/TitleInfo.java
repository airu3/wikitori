package model;

/**
 * Wikipediaのタイトル情報を格納するクラス
 * 
 */
public class TitleInfo {
	private final int pageId;
	private final String title;

	/**
	 * コンストラクタ
	 * 
	 * @param pageId ページID
	 * @param title  タイトル
	 */
	public TitleInfo(int pageId, String title) {
		this.pageId = pageId;
		this.title = title;
	}

	/**
	 * ページIDを取得
	 * 
	 * @return ページID
	 */
	public int getPageId() {
		return pageId;
	}

	/**
	 * タイトルを取得
	 * 
	 * @return タイトル
	 */
	public String getTitle() {
		return title;
	}

}