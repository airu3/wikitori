package model;

/**
 * Wikipediaのタイトル情報を格納するクラス
 * 
 */
public class TitleInfo {
	private final int pageId;
	private final String title;

	public TitleInfo(int pageId, String title) {
		this.pageId = pageId;
		this.title = title;
	}

	public int getPageId() {
		return pageId;
	}

	public String getTitle() {
		return title;
	}

}