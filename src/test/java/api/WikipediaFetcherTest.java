package api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import model.TitleInfo;

class WikipediaFetcherTest {

	@Test
	void FetchTest() {
		try {
			List<CompletableFuture<List<TitleInfo>>> futures = new ArrayList<>();

			// Example usage with multiple searches
			futures.add(WikipediaFetcher.fetchWordInfo("ご", 200, new ArrayList<>()));
			futures.add(WikipediaFetcher.fetchWordInfo("ゴ", 200, new ArrayList<>()));

			// Wait for all futures to complete
			CompletableFuture<Void> allOf = CompletableFuture.allOf(
					futures.toArray(new CompletableFuture[0]));

			// Join all completed futures
			allOf.get();

			// Process the results
			for (CompletableFuture<List<TitleInfo>> future : futures) {
				List<TitleInfo> result = future.get();
				// Handle the result as needed
				for (TitleInfo wordInfo : result) {
					System.out.println("Page ID: " + wordInfo.getPageId() + ", Word: " + wordInfo.getTitle());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
