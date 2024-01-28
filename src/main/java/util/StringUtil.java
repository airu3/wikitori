package util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class StringUtil {

	// 捨て仮名をひらがなに変換
	private static final Map<String, String> HIRAGANA_SMALL_TO_LARGE = new HashMap<>() {
		{
			put("ぁ", "あ");
			put("ぃ", "い");
			put("ぅ", "う");
			put("ぇ", "え");
			put("ぉ", "お");
			put("ゕ", "か");
			put("ゖ", "け");
			put("っ", "つ");
			put("ゃ", "や");
			put("ゅ", "ゆ");
			put("ょ", "よ");
			put("ゎ", "わ");
		}
	};

	// 捨て仮名をカタカナに変換
	private static final Map<String, String> KATAKANA_SMALL_TO_LARGE = new HashMap<>() {
		{
			put("ァ", "ア");
			put("ィ", "イ");
			put("ゥ", "ウ");
			put("ェ", "エ");
			put("ォ", "オ");
			put("ヵ", "カ");
			put("ヶ", "ケ");
			put("ッ", "ツ");
			put("ャ", "ヤ");
			put("ュ", "ユ");
			put("ョ", "ヨ");
			put("ヮ", "ワ");
		}
	};

	/**
	 * 小文字を大文字に変換
	 * 
	 * @param character 文字
	 * @param map       変換マップ
	 * @return 変換後の文字
	 */
	private static String convertSmallToLarge(String character, Map<String, String> map) {
		return map.getOrDefault(character, character);
	}

	// 修飾文字ではない任意の文字または数字の正規表現
	private static final String REGEX_NOT_MODIFIER = "(?:(?!\\p{Lm})\\p{L})|\\p{N}";

	/**
	 * 指定した正規表現に一致するまで文字列の最後を削る
	 * 
	 * @param str 文字列
	 * @param p   正規表現
	 * @return 正規表現に一致するまで文字列の最後を削った文字列
	 */
	public static String trimWordFromEnd(String str, Pattern p) {
		while (str.length() > 0 && !p.matcher(extractLastChar(str)).matches()) {
			str = removeLastChar(str);
			System.out.println("T " + str); // ログ出力
		}
		return str;
	}

	/**
	 * 文字列の最初を抽出
	 * 
	 * @param str 文字列
	 * @return 文字列の最初
	 */
	public static String extractFirstChar(String str) {
		return str.substring(0, 1);
	}

	/**
	 * 文字列の最後を抽出
	 * 
	 * @param str 文字列
	 * @return 文字列の最後
	 */
	public static String extractLastChar(String str) {
		if (str.length() == 0) {
			return "";
		}
		return str.substring(str.length() - 1);
	}

	/**
	 * 文字列の最後を削除
	 * 
	 * @param str 文字列
	 * @return 最後を削除した文字列
	 */
	public static String removeLastChar(String str) {
		if (str.length() == 0) {
			return "";
		}
		return str.substring(0, str.length() - 1);
	}

	/**
	 * しりとりの単語を処理する
	 * 
	 * @param inputWord しりとりの単語
	 * @param flag      しりとりの単語の最初か最後かを表すフラグ
	 * @return 処理後の単語
	 */
	public static String[] processJapaneseWord(String inputWord, int flag) {
		// flag = 1: しりとりの単語の最初を取得
		// flag = -1: しりとりの単語の最後を取得
		String inputChar;
		if (flag == 1) {
			inputChar = extractFirstChar(inputWord);
		} else {
			inputChar = extractLastChar(inputWord);
		}

		System.out.println("Before processing, word is: " + inputWord);

		// ひらがなとカタカナを
		String[] resultChar = new String[2];

		// ひらがな、カタカナ、漢字,数字,記号の場合に分けて処理
		if (JapaneseConverter.isHiragana(inputChar)) {
			// ひらがなの場合
			resultChar[0] = inputChar;
			resultChar[1] = JapaneseConverter.convertHiraToKata(inputChar);
			System.out.println("After processing hiragana, resultChar is: " + Arrays.toString(resultChar));
		} else if (JapaneseConverter.isKatakana(inputChar)) {
			// カタカナの場合
			resultChar[0] = JapaneseConverter.convertKataToHira(inputChar);
			resultChar[1] = inputChar;
			System.out.println("After processing katakana, resultChar is: " + Arrays.toString(resultChar));
		} else {
			// 漢字, 数字, 記号の場合
			// 音読可能な部分までを抽出
			Pattern p = Pattern.compile(REGEX_NOT_MODIFIER);
			String trimmedWord = trimWordFromEnd(inputWord, p);
			String hiragana = JapaneseConverter.convertAllToHira(trimmedWord);
			System.out.println("After processing kanji, hiragana is: " + hiragana);

			if (flag == -1) {
				hiragana = extractLastChar(hiragana);
			} else {
				hiragana = extractFirstChar(hiragana);
			}

			resultChar[0] = hiragana;
			resultChar[1] = JapaneseConverter.convertHiraToKata(hiragana);
			System.out.println("After processing kanji, resultChar is: " + Arrays.toString(resultChar));
		}

		// 小文字を大文字に変換
		resultChar[0] = convertSmallToLarge(resultChar[0], HIRAGANA_SMALL_TO_LARGE);
		resultChar[1] = convertSmallToLarge(resultChar[1], KATAKANA_SMALL_TO_LARGE);

		System.out.println("Final state of resultChar is: " + Arrays.toString(resultChar));
		return resultChar;
	}

}
