package api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import model.WordInfo;

class WikipediaFetcherTest {

	@Test
	void FetchTest() {
		try {
			List<CompletableFuture<List<WordInfo>>> futures = new ArrayList<>();

			// Example usage with multiple searches
			futures.add(WikipediaFetcher.fetchWordsFromWikipedia("ご", 200, new ArrayList<>()));
			futures.add(WikipediaFetcher.fetchWordsFromWikipedia("ゴ", 200, new ArrayList<>()));

			// Wait for all futures to complete
			CompletableFuture<Void> allOf = CompletableFuture.allOf(
					futures.toArray(new CompletableFuture[0]));

			// Join all completed futures
			allOf.get();

			// Process the results
			for (CompletableFuture<List<WordInfo>> future : futures) {
				List<WordInfo> result = future.get();
				// Handle the result as needed
				for (WordInfo wordInfo : result) {
					System.out.println("Page ID: " + wordInfo.getPageId() + ", Word: " + wordInfo.getWord());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
