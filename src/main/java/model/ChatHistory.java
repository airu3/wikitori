package model;

/**
 * チャット履歴を格納するクラス
 */
public class ChatHistory {
	private String speaker;
	private String message;

	/**
	 * コンストラクタ
	 * 
	 * @param speaker 発言者
	 * @param message メッセージ
	 */
	public ChatHistory(String speaker, String message) {
		this.speaker = speaker;
		this.message = message;
	}

	/**
	 * 発言者を取得
	 * 
	 * @return 発言者
	 */
	public String getSpeaker() {
		return speaker;
	}

	/**
	 * メッセージを取得
	 * 
	 * @return メッセージ
	 */
	public String getMessage() {
		return message;
	}
}
