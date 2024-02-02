package util;

import java.util.ArrayList;
import java.util.List;

public class NgWordManager {
	private static List<String> ngWords;
	// wikipedia検索時に1/300の確率で見事にヒットしてしまったので追加
	static {
		ngWords = new ArrayList<>();
		addNgWord("アナル");
		addNgWord("アダルトビデオ");
		addNgWord("アダルトゲーム");
		addNgWord("うつ病");
		addNgWord("うつ病の治療");
		addNgWord("オナニー");
		addNgWord("オナホール");
		addNgWord("コンドーム");
		addNgWord("ばにしゅ! 〜おっぱいの消えた王国〜");
		addNgWord("ポルノ映画");
		addNgWord("まんこ");
		addNgWord("リベンジポルノ");
	}

	/**
	 * NGワードを追加
	 * 
	 * @param word 追加するNGワード
	 */
	public static void addNgWord(String word) {
		ngWords.add(word);
	}

	/**
	 * NGワードを削除
	 * 
	 * @param word 削除するNGワード
	 * 
	 */
	public static void removeNgWord(String word) {
		ngWords.remove(word);
	}

	/**
	 * NGワードかどうかをチェック
	 * 
	 * @param word チェックするワード
	 * @return NGワードの場合はtrue
	 * 
	 */
	public static boolean isNgWord(String word) {
		return ngWords.contains(word);
	}

	/**
	 * NGワードを含むかどうかをチェック
	 * 
	 * @param word チェックするワード
	 * @return NGワードを含む場合はtrue
	 * 
	 */
	public static boolean isContains(String word) {
		return ngWords.contains(word);
	}

	/**
	 * NGワードの一覧を取得
	 * 
	 * @return NGワードの一覧
	 */
	public static List<String> getNgWords() {
		return ngWords;
	}

	/**
	 * NGワードの一覧を設定
	 * 
	 * @param ngWords NGワードの一覧
	 */
	public static void setNgWords(List<String> ngWords) {
		NgWordManager.ngWords = ngWords;
	}

}