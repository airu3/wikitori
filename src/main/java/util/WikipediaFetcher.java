package util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WikipediaFetcher {

	public static CompletableFuture<List<String>> fetchWordsFromWikipedia(String searchTerm, List<String> ngWords) {
		System.out.println(searchTerm);

		String url = String.format(
				"https://ja.wikipedia.org/w/api.php?format=json&action=query&list=prefixsearch&pssearch=%s&pslimit=200&psnamespace=0",
				searchTerm);

		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.build();

		return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
				.thenApply(HttpResponse::body)
				.thenApply(WikipediaFetcher::processJson)
				.thenApply(result -> filterWords(result, ngWords));
	}

	private static List<String> processJson(String json) {
		// Implement the logic to extract words and page links from the JSON response
		// ...

		List<String> words = new ArrayList<>();
		// words.add(word);

		List<String> links = new ArrayList<>();
		// links.add(link);

		// Example: return a list of words
		return words;
	}

	private static List<String> filterWords(List<String> words, List<String> ngWords) {
		// Implement the logic to filter out words based on ngWords
		// ...

		List<String> filteredWords = new ArrayList<>();
		for (String word : words) {
			if (!ngWords.contains(word)) {
				filteredWords.add(word);
			}
		}

		// Example: return a filtered list of words
		return filteredWords;
	}

}
