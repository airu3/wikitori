package api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.TitleInfo;

public class WikipediaFetcher {

	public static CompletableFuture<List<TitleInfo>> fetchWordInfo(String searchTerm, int limit,
			List<String> ngWords) {
		return CompletableFuture.supplyAsync(() -> {
			List<TitleInfo> results = new ArrayList<>();

			try {
				// 検索語をURLエンコード
				String encodedSearchTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8.toString());

				String apiUrl = "https://ja.wikipedia.org/w/api.php?format=json&action=query&list=prefixsearch&pssearch=" +
						encodedSearchTerm + "&pslimit=" + limit + "&psnamespace=0";
				URL url = new URL(apiUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setConnectTimeout(10000);

				try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}

					JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
					JsonArray prefixSearch = jsonResponse.getAsJsonObject("query").getAsJsonArray("prefixsearch");

					for (int i = 0; i < prefixSearch.size(); i++) {
						JsonObject value = prefixSearch.get(i).getAsJsonObject();
						String title = value.get("title").getAsString();

						// Processing word
						if (!title.equals(searchTerm)) {
							String word = title.replaceAll(" *\\([^)]*\\) *", "");
							if (!ngWords.contains(word)) {
								results.add(new TitleInfo(value.get("pageid").getAsInt(), word));
							} else {
								System.out.println("Word is in NG_words, not adding to results");
							}
						} else {
							System.out.println("Word is the same as searchTerm, not processing");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return results;
		});
	}
}
