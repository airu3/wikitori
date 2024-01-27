package util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

public class KanjiToHiraganaConverter {
	private static final String API_URL = "https://labs.goo.ne.jp/api/hiragana";
	private static final String API_KEY = "d5b86171fcdc098cd38e9b056f8c46c84ec367c171b29ec686f3307e0f3030ef";

	public static String convertToHiragana(String inputWord) {
		try {
			URL url = new URL(API_URL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			String requestBody = "{\"app_id\":\"" + API_KEY + "\",\"sentence\":\"" + inputWord
					+ "\",\"output_type\":\"hiragana\"}";

			try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
				wr.writeBytes(requestBody);
			}

			int responseCode = connection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = in.readLine()) != null) {
						response.append(line);
					}

					return response.toString();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ""; // 変換に失敗した場合は空文字を返すか、エラー処理を行う
	}

}
