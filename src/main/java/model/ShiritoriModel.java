package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import api.WikipediaFetcher;
import util.StringUtil;

public class ShiritoriModel {
	private static final Logger logger = Logger.getLogger(ShiritoriModel.class.getName());

	public TitleInfo playShiritori(String userMsg) {
		TitleInfo result;
		// 最後の文字を取得
		String[] changes = StringUtil.processJapaneseWord(userMsg, -1);

		try {
			List<CompletableFuture<List<TitleInfo>>> futures = new ArrayList<>();

			// Example usage with multiple searches
			futures.add(WikipediaFetcher.fetchWordInfo(changes[0], 150, new ArrayList<>()));
			futures.add(WikipediaFetcher.fetchWordInfo(changes[1], 150, new ArrayList<>()));

			// Wait for all futures to complete
			CompletableFuture<Void> allOf = CompletableFuture.allOf(
					futures.toArray(new CompletableFuture[0]));

			// Join all completed futures
			allOf.get();

			// 単語がない場合は負け
			if (futures.get(0).get().isEmpty() && futures.get(1).get().isEmpty()) {
				logger.info("No words found, you lose.");
				return new TitleInfo(-1, "負けました");
			} else {
				do {
					int index = (int) (Math.random() * futures.size());
					CompletableFuture<List<TitleInfo>> randFuture = futures.get(index);
					result = randFuture.get().get((int) (Math.random() * randFuture.get().size()));
					// 選択された単語の最後が「ん」以外になるまで繰り返す
				} while (StringUtil.processJapaneseWord(result.getTitle(), -1)[0].equals("ん"));
				logger.info("Selected word: " + result.getTitle());
				return result;
			}

		} catch (Exception e) {
			logger.severe("Exception occurred: " + e.getMessage());
			e.printStackTrace();
			// 完全敗北exception
			return new TitleInfo(-1, "負けました");
		}

	}

}