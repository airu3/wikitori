package util;

import java.util.ArrayList;
import java.util.List;

public class NgWordManager {
	private static List<String> ngWords;
	static {
		ngWords = new ArrayList<>();
		addNgWord("アナル");
		addNgWord("アダルトビデオ");
		addNgWord("アダルトゲーム");
		addNgWord("うつ病");
		addNgWord("オナニー");
		addNgWord("オナホール");
		addNgWord("コンドーム");
		addNgWord("ばにしゅ! 〜おっぱいの消えた王国〜");
		addNgWord("ポルノ映画");
		addNgWord("まんこ");
		addNgWord("リベンジポルノ");
	}

	// NGワードを追加
	public static void addNgWord(String word) {
		ngWords.add(word);
	}

	// NGワードを削除
	public static void removeNgWord(String word) {
		ngWords.remove(word);
	}

	// 指定したワードがNGワードかどうかをチェック
	public static boolean isNgWord(String word) {
		return ngWords.contains(word);
	}

	// 含まれていますか？
	public static boolean isContain(String word) {
		return ngWords.contains(word);
	}

	// NGワードの一覧を取得
	public static List<String> getNgWords() {
		return ngWords;
	}

	// NGワードの一覧を設定
	public static void setNgWords(List<String> ngWords) {
		NgWordManager.ngWords = ngWords;
	}

}