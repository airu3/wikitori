package util;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class KanjiToHiraganaConverter {
	private static final String API_URL = "https://labs.goo.ne.jp/api/hiragana";
	private static final String API_KEY = "d5b86171fcdc098cd38e9b056f8c46c84ec367c171b29ec686f3307e0f3030ef";

	public static String convertToHiragana(String inputWord) {
		try {
			URL url = new URL(API_URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(10000);
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");

			String jsonInputString = "{\"app_id\": \"" + API_KEY + "\", \"sentence\": \"" + inputWord
					+ "\", \"output_type\": \"hiragana\"}";

			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);
			}

			try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}

				// 変換後のひらがな部分だけを取り出して返す
				return extractConvertedHiragana(response.toString());
			} finally {
				connection.disconnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * APIのレスポンスから変換後のひらがな部分だけを取り出す
	 * 
	 * @param json APIのレスポンス
	 * @return 変換後のひらがな
	 */
	private static String extractConvertedHiragana(String json) {
		// "converted"の位置を検索
		int startIndex = json.indexOf("\"converted\": ") + 13;
		int endIndex = json.indexOf("\",", startIndex);

		// "converted"の値を取り出す
		String convertedValue = json.substring(startIndex, endIndex);

		// 先頭の"を削除
		convertedValue = convertedValue.substring(1);

		return convertedValue;
	}

}
