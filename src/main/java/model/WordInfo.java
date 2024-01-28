package model;

public class WordInfo {
	private final String word;
	private final String pageId;

	public WordInfo(String word, String pageId) {
		this.word = word;
		this.pageId = pageId;
	}

	public String getWord() {
		return word;
	}

	public String getPageId() {
		return pageId;
	}
}