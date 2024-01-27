package util;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringUtil {

	private static final Map<String, String> HIRAGANA_SMALL_TO_LARGE = new HashMap<>() {
		{
			put("ぁ", "あ");
			put("ぃ", "い");
			put("ぅ", "う");
			put("ぇ", "え");
			put("ぉ", "お");
			put("っ", "つ");
			put("ゃ", "や");
			put("ゅ", "ゆ");
			put("ょ", "よ");
			put("ゎ", "わ");
		}
	};

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

	// 文字列の最初を抽出
	public static String getFirstChar(String inputWord) {
		return inputWord.substring(0, 1);
	}

	// 文字列の最後を抽出
	public static String getLastChar(String inputWord) {
		return inputWord.substring(inputWord.length() - 1);
	}

	public static String[] strChange(String inputWord, int flag) {
		// flag = 1: しりとりの単語の最初を取得
		// flag = -1: しりとりの単語の最後を取得
		String inputChar;
		if (flag == 1) {
			inputChar = getFirstChar(inputWord);
		} else {
			inputChar = getLastChar(inputWord);
		}

		System.out.println("Before processing, word is: " + inputWord);

		// ひらがなとカタカナを
		String[] resultChar = new String[2];

		// ひらがな、カタカナ、漢字の場合に分けて処理

		if (JapaneseCharConverter.isHiragana(inputChar)) {
			// ひらがなの場合
			resultChar[0] = inputChar;
			resultChar[1] = JapaneseCharConverter.convertH2K(inputChar);
			System.out.println("After processing hiragana, resultChar is: " + Arrays.toString(resultChar));
		} else if (JapaneseCharConverter.isKatakana(inputChar)) {
			// カタカナの場合
			resultChar[0] = JapaneseCharConverter.convertK2H(inputChar);
			resultChar[1] = inputChar;
			System.out.println("After processing katakana, resultChar is: " + Arrays.toString(resultChar));
		} else {
			// 漢字, 数字, 記号の場合
			String hiragana = KanjiToHiraganaConverter.convertToHiragana(inputWord);

			resultChar[0] = hiragana;
			resultChar[1] = JapaneseCharConverter.convertH2K(hiragana);
			System.out.println("After processing kanji, resultChar is: " + Arrays.toString(resultChar));
		}

		// 小文字を大文字に変換
		resultChar[0] = convertSmallToLarge(resultChar[0], HIRAGANA_SMALL_TO_LARGE);
		resultChar[1] = convertSmallToLarge(resultChar[1], KATAKANA_SMALL_TO_LARGE);

		System.out.println("Final state of resultChar is: " + Arrays.toString(resultChar));
		return new String[] { Character.toString(resultChar[0]), Character.toString(resultChar[1]) };
	}

	private static String convertKanjiToHiragana(String word, int[] range) {
		try {
			URL url = new URL("https://labs.goo.ne.jp/api/hiragana");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(10000);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			String requestBody = String.format("{\"app_id\":\"%s\", \"sentence\":\"%s\", \"output_type\":\"hiragana\"}",
					"d5b86171fcdc098cd38e9b056f8c46c84ec367c171b29ec686f3307e0f3030ef", word);

			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				// API呼び出し成功時の処理
				// APIのレスポンスを解析してひらがなに変換
				// ここでは仮に固定のひらがなを返すようにしています
				return "ひらがな";
			} else {
				// API呼び出し失敗時の処理
				System.err.println("API call failed with response code: " + responseCode);
				return word; // エラーが発生した場合は元の文字列を返す
			}
		} catch (Exception e) {
			// 例外が発生した場合の処理
			e.printStackTrace();
			return word; // エラーが発生した場合は元の文字列を返す
		}
	}

	// ... (後続のコードも省略)

	private static char convertSmallToLarge(char character, Map<Character, Character> map) {
		return map.getOrDefault(character, character);
	}

	// !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~
	private static final String SYMBOLS_REGEX = "[!\"#$%&'()*+,-./:;<=>?@[\\\\]^_`{|}~]";

	public static String sanitizeSymbols(String input) {
		// \b \t \n \f \r \" \' \\
		return input
				.replaceAll(SYMBOLS_REGEX, "")
				.replace("\b", "")
				.replace("\t", "")
				.replace("\n", "")
				.replace("\f", "")
				.replace("\r", "")
				.replace("\"", "")
				.replace("'", "")
				.replace("\\", "");
	}
}
