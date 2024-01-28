package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import api.HiraganaConverter;

public class JapaneseConverter {
	// ひらがな-カタカナ間のUnicode値の差（以下全て16進数）
	private static final int DIFFERENCE_NUM = 0x60;
	private static final int START_HIRAGANA_CODE = 0x3041; // ぁ
	private static final int END_HIRAGANA_CODE = 0x3096; // ゖ
	private static final int START_KATANAKA_CODE = 0x30A1; // ァ
	private static final int END_KATAKANA_CODE = 0x30FA; // ヶ

	// API に変換処理を委譲
	HiraganaConverter hiraganaConverter = new HiraganaConverter();

	/**
	 * ひらがなをカタカナに変換
	 * 
	 * @param str 変換前の文字列
	 * @return 変換後の文字列
	 */
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

	/**
	 * カタカナをひらがなに変換
	 * 
	 * @param str 変換前の文字列
	 * @return 変換後の文字列
	 */
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

	/**
	 * ひらがなかどうか判定
	 * 
	 * @param str 判定対象の文字列
	 * @return ひらがなの場合は true
	 */
	public static boolean isHiragana(String str) {
		return str.codePoints()
				.allMatch(codePoint -> codePoint >= START_HIRAGANA_CODE && codePoint <= END_HIRAGANA_CODE);
	}

	/**
	 * カタカナかどうか判定
	 * 
	 * @param str 判定対象の文字列
	 * @return カタカナの場合は true
	 */
	public static boolean isKatakana(String str) {
		return str.codePoints()
				.allMatch(codePoint -> codePoint >= START_KATANAKA_CODE && codePoint <= END_KATAKANA_CODE);
	}

	/**
	 * ひらがなをカタカナに変換(メソッドの別名定義)
	 * 
	 * @param str 変換前の文字列
	 * @return 変換後の文字列
	 */
	public static String convertHiraToKata(String str) {
		return convertHiraganaToKatakana(str);
	}

	/**
	 * ひらがなをカタカナに変換(メソッドの別名定義)
	 * 
	 * @param str 変換前の文字列
	 * @return 変換後の文字列
	 */
	public static String convertH2K(String str) {
		return convertHiraganaToKatakana(str);
	}

	/**
	 * カタカナをひらがなに変換(メソッドの別名定義)
	 * 
	 * @param str 変換前の文字列
	 * @return 変換後の文字列
	 */
	public static String convertKataToHira(String str) {
		return convertKatakanaToHiragana(str);
	}

	/**
	 * カタカナをひらがなに変換(メソッドの別名定義)
	 * 
	 * @param str 変換前の文字列
	 * @return 変換後の文字列
	 */
	public static String convertK2H(String str) {
		return convertKatakanaToHiragana(str);
	}

	/**
	 * すべてひらがなに変換
	 * 
	 * @param str 変換前の文字列
	 * @return 変換後の文字列
	 */
	public static String convertAllToHiragana(String str) {
		return HiraganaConverter.convertToHiragana(str);
	}

	/**
	 * すべてひらがなに変換(メソッドの別名定義)
	 * 
	 * @param str 変換前の文字列
	 * @return 変換後の文字列
	 */
	public static String convertAllToHira(String str) {
		return HiraganaConverter.convertToHiragana(str);
	}

	/**
	 * すべてひらがなに変換(メソッドの別名定義)
	 * 
	 * @param str 変換前の文字列
	 * @return 変換後の文字列
	 */
	public static String convertA2H(String str) {
		return HiraganaConverter.convertToHiragana(str);
	}

}
