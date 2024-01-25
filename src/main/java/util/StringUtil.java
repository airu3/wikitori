package util;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringUtil {

	private static final Map<Character, Character> HIRAGANA_SMALL_TO_LARGE = new HashMap<>() {
		{
			put('ぁ', 'あ');
			put('ぃ', 'い');
			put('ぅ', 'う');
			put('ぇ', 'え');
			put('ぉ', 'お');
			put('っ', 'つ');
			put('ゃ', 'や');
			put('ゅ', 'ゆ');
			put('ょ', 'よ');
			put('ゎ', 'わ');
		}
	};

	private static final Map<Character, Character> KATAKANA_SMALL_TO_LARGE = new HashMap<>() {
		{
			put('ァ', 'ア');
			put('ィ', 'イ');
			put('ゥ', 'ウ');
			put('ェ', 'エ');
			put('ォ', 'オ');
			put('ヵ', 'カ');
			put('ヶ', 'ケ');
			put('ッ', 'ツ');
			put('ャ', 'ヤ');
			put('ュ', 'ユ');
			put('ョ', 'ヨ');
			put('ヮ', 'ワ');
		}
	};

	public static String[] strChange(String inputWord, int flag) {
		int[] range = (flag == 1) ? new int[] { 0, 1 } : new int[] { -1, -1 };

		char[] hiragana = generateCharRange('\u3041', '\u3096'); // ひらがな
		char[] katakana = generateCharRange('\u30a1', '\u30f6'); // カタカナ

		char[] inputChars = inputWord.toCharArray();
		System.out.println("Before processing, word is: " + inputWord);

		char[] r = new char[2];
		if (Arrays.binarySearch(hiragana, inputChars[range[0]]) >= 0) {
			// ひらがなの場合
			r[0] = inputChars[range[0]];
			r[1] = katakana[Arrays.binarySearch(hiragana, inputChars[range[0]])];
			System.out.println("After processing hiragana, r is: " + Arrays.toString(r));
		} else if (Arrays.binarySearch(katakana, inputChars[range[0]]) >= 0) {
			// カタカナの場合
			r[0] = hiragana[Arrays.binarySearch(katakana, inputChars[range[0]])];
			r[1] = inputChars[range[0]];
			System.out.println("After processing katakana, r is: " + Arrays.toString(r));
		} else {
			// 漢字の場合
			String convertedWord = convertKanjiToHiragana(inputWord, range);
			r[0] = convertedWord.charAt(range[0]);
			r[1] = katakana[Arrays.binarySearch(hiragana, convertedWord.charAt(range[0]))];
			System.out.println("After processing kanji, r is: " + Arrays.toString(r));
		}

		// 小文字を大文字に変換
		r[0] = convertSmallToLarge(r[0], HIRAGANA_SMALL_TO_LARGE);
		r[1] = convertSmallToLarge(r[1], KATAKANA_SMALL_TO_LARGE);

		System.out.println("Final state of r is: " + Arrays.toString(r));
		return new String[] { Character.toString(r[0]), Character.toString(r[1]) };
	}

	private static char[] generateCharRange(char start, char end) {
		char[] range = new char[end - start + 1];
		for (int i = 0; i <= end - start; i++) {
			range[i] = (char) (start + i);
		}
		return range;
	}

	private static String convertKanjiToHiragana(String word, int[] range) {
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
}
	}

	private static char convertSmallToLarge(char character, Map<Character, Character> map) {
		return map.getOrDefault(character, character);
	}

}
