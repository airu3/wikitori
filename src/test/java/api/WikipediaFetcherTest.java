package api;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;

import model.TitleInfo;
import util.JapaneseConverter;

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

	@Test // ひらがなの一覧を生成し、それぞれの単語に対して検索を行う
	void FetchAllHiraganaTest() {
		try {
			// ひらがなの一覧を生成
			String[] hiraganas = JapaneseConverter.createHiraganaList();

			// ひらがなごとに検索を行う
			for (String hiragana : hiraganas) {
				List<TitleInfo> result = WikipediaFetcher.fetchWordInfo(hiragana, 200, new ArrayList<>()).get();

				// タイトルの順番をソート
				result.sort(Comparator.comparing(TitleInfo::getTitle));

				// 一つのリストに結合した結果を出力
				for (TitleInfo titleInfo : result) {
					System.out.printf("\t id :%8d , title : %s\n", titleInfo.getPageId(), titleInfo.getTitle());
				}

				// 適切な間隔を設定してDoS攻撃にならないようにする
				Thread.sleep(1000); // 1秒待つ
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test // カタカナの一覧を生成し、それぞれの単語に対して検索を行う
	void FetchAllKatakanaTest() {
		try {
			// カタカナの一覧を生成
			String[] katakanas = JapaneseConverter.createKatakanaList();

			// カタカナごとに検索を行う
			for (String katakana : katakanas) {
				List<TitleInfo> result = WikipediaFetcher.fetchWordInfo(katakana, 200, new ArrayList<>()).get();

				// タイトルの順番をソート
				result.sort(Comparator.comparing(TitleInfo::getTitle));

				// 一つのリストに結合した結果を出力
				for (TitleInfo titleInfo : result) {
					System.out.printf("\t id :%8d , title : %s\n", titleInfo.getPageId(), titleInfo.getTitle());
				}

				// 適切な間隔を設定してDoS攻撃にならないようにする
				Thread.sleep(1000); // 1秒待つ
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
