package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import api.WikipediaFetcher;
import util.NgWordManager;
import util.StringUtil;

public class ShiritoriModel {
	private static final Logger logger = Logger.getLogger(ShiritoriModel.class.getName());
	private static List<String> ngWords = NgWordManager.getNgWords();
	private List<String> usedWords = new ArrayList<>(); // 既に使用した単語を保存するリスト

	public TitleInfo playShiritori(String userMsg) {
		TitleInfo result;
		// 最後の文字を取得
		String[] changes = StringUtil.processJapaneseWord(userMsg, -1);

		// 使用済みの単語を追加
		usedWords.add(userMsg);

		try {
			List<CompletableFuture<List<TitleInfo>>> futures = new ArrayList<>();

			// NGワードと既に使用した単語を結合
			List<String> filterWords = new ArrayList<>();
			filterWords.addAll(ngWords);
			filterWords.addAll(usedWords);

			// それぞれの単語の情報を取得する
			futures.add(WikipediaFetcher.fetchWordInfo(changes[0], 5, filterWords));
			futures.add(WikipediaFetcher.fetchWordInfo(changes[1], 5, filterWords));

			// すべてのCompletableFutureが完了するまで待機する
			CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

			// それぞれのCompletableFutureの結果を一つのリストに結合する
			List<TitleInfo> allResults = new ArrayList<>();
			for (CompletableFuture<List<TitleInfo>> future : futures) {
				allResults.addAll(future.get());
			}

			// 単語がない場合は負け
			if (allResults.isEmpty()) {
				logger.info("No words found, you lose.");
				return new TitleInfo(-1, "負けました");
			} else {
				do {
					int index = (int) (Math.random() * allResults.size());
					result = allResults.get(index);

					// タイトルの順番をソート
					allResults.sort(Comparator.comparing(TitleInfo::getTitle));

					// 一つのリストに結合した結果を出力
					for (TitleInfo titleInfo : allResults) {
						System.out.printf("\t id :%8d , title : %s\n", titleInfo.getPageId(), titleInfo.getTitle());
					}
					// 選択された単語の最後が「ん」以外になるまで繰り返す
				} while (StringUtil.processJapaneseWord(result.getTitle(), -1)[0].equals("ん"));
				System.out.println("選択された単語は" + result.getTitle() + "です。");
				return result;
			}
			// 完全敗北exception
		} catch (Exception e) {
			logger.severe("完全敗北Exception occurred: " + e.getMessage());
			e.printStackTrace();
			return new TitleInfo(-1, "負けました");
		}

	}

}