package util;

import java.util.ArrayList;
import java.util.List;

public class NgWordManager {
	private static final List<String> ngWords = new ArrayList<>();

	// wikipediaのNGワード
	static {
		ngWords.add("アダルトビデオ");
		ngWords.add("アダルトゲーム");
		ngWords.add("いじめ");
		ngWords.add("いじめるヤバイ奴");
		ngWords.add("うつ病");
		ngWords.add("うつ病の治療");
		ngWords.add("エロマンガ先生");
		ngWords.add("オナニー");
		ngWords.add("オナホール");
		ngWords.add("クモ");
		ngWords.add("リベンジポルノ");
		ngWords.add("んこダイス");
	}

	/**
	 * NGワードかどうかを判定
	 * 
	 * @param word 単語
	 * @return NGワードの場合はtrue
	 */
	public static boolean isNgWord(String word) {
		return ngWords.contains(word);
	}

}