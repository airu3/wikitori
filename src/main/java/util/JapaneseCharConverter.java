package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JapaneseCharConverter {
	// ひらがな-カタカナ間のUnicode値の差（以下全て16進数）
	private static final int DIFFERENCE_NUM = 0x60;
	private static final int START_HIRAGANA_CODE = 0x3041; // ぁ
	private static final int END_HIRAGANA_CODE = 0x3096; // ゖ
	private static final int START_KATANAKA_CODE = 0x30A1; // ァ
	private static final int END_KATAKANA_CODE = 0x30FA; // ヶ

	public static String convertKatakanaToHiragana(String str) {
		Pattern pattern = Pattern.compile("[\\u30a1-\\u30fa]");
		Matcher matcher = pattern.matcher(str);
		StringBuffer result = new StringBuffer();

		while (matcher.find()) {
			char chr = (char) (matcher.group().codePointAt(0) - DIFFERENCE_NUM);
			matcher.appendReplacement(result, String.valueOf(chr));
		}
		matcher.appendTail(result);

		return result.toString();
	}

	public static String convertHiraganaToKatakana(String str) {
		Pattern pattern = Pattern.compile("[\\u3041-\\u3096]");
		Matcher matcher = pattern.matcher(str);
		StringBuffer result = new StringBuffer();

		while (matcher.find()) {
			char chr = (char) (matcher.group().codePointAt(0) + DIFFERENCE_NUM);
			matcher.appendReplacement(result, String.valueOf(chr));
		}
		matcher.appendTail(result);

		return result.toString();
	}

	public static boolean isHiragana(String str) {
		return str.codePoints()
				.allMatch(codePoint -> codePoint >= START_HIRAGANA_CODE && codePoint <= END_HIRAGANA_CODE);
	}

	public static boolean isKatakana(String str) {
		return str.codePoints()
				.allMatch(codePoint -> codePoint >= START_KATANAKA_CODE && codePoint <= END_KATAKANA_CODE);
	}

	// メソッドの別名定義
	public static String convertHiraToKata(String str) {
		return convertHiraganaToKatakana(str);
	}

	public static String convertH2K(String str) {
		return convertHiraganaToKatakana(str);
	}

	public static String convertKataToHira(String str) {
		return convertKatakanaToHiragana(str);
	}

	public static String convertK2H(String str) {
		return convertKatakanaToHiragana(str);
	}

}
