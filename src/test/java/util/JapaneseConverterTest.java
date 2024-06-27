package util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class JapaneseConverterTest {

	@Test
	public void testConvertKatakanaToHiragana() {
		String katakana = "アイウエオ";
		String expectedHiragana = "あいうえお";
		String convertedHiragana = JapaneseConverter.convertKatakanaToHiragana(katakana);
		assertEquals(expectedHiragana, convertedHiragana);
	}

	@Test
	public void testConvertHiraganaToKatakana() {
		String hiragana = "あいうえお";
		String expectedKatakana = "アイウエオ";
		String convertedKatakana = JapaneseConverter.convertHiraganaToKatakana(hiragana);
		assertEquals(expectedKatakana, convertedKatakana);
	}

	@Test
	public void testIsHiragana() {
		String hiragana = "あいうえお";
		assertTrue(JapaneseConverter.isHiragana(hiragana));

		String katakana = "アイウエオ";
		assertFalse(JapaneseConverter.isHiragana(katakana));
	}

	@Test
	public void testIsKatakana() {
		String katakana = "アイウエオ";
		assertTrue(JapaneseConverter.isKatakana(katakana));

		String hiragana = "あいうえお";
		assertFalse(JapaneseConverter.isKatakana(hiragana));
	}

	@Test
	public void testConvertHiraToKata() {
		String hiragana = "あいうえお";
		String expectedKatakana = "アイウエオ";
		String convertedKatakana = JapaneseConverter.convertHiraToKata(hiragana);
		assertEquals(expectedKatakana, convertedKatakana);
	}

	@Test
	public void testConvertH2K() {
		String hiragana = "あいうえお";
		String expectedKatakana = "アイウエオ";
		String convertedKatakana = JapaneseConverter.convertH2K(hiragana);
		assertEquals(expectedKatakana, convertedKatakana);
	}

	@Test
	public void testConvertKataToHira() {
		String katakana = "アイウエオ";
		String expectedHiragana = "あいうえお";
		String convertedHiragana = JapaneseConverter.convertKataToHira(katakana);
		assertEquals(expectedHiragana, convertedHiragana);
	}

	@Test
	public void testConvertK2H() {
		String katakana = "アイウエオ";
		String expectedHiragana = "あいうえお";
		String convertedHiragana = JapaneseConverter.convertK2H(katakana);
		assertEquals(expectedHiragana, convertedHiragana);
	}

	@Test
	public void testConvertAllToHiragana() {
		String input = "&lt今日は&gt!".replace("\n", "\\n");
		String convertedHiragana = JapaneseConverter.convertAllToHiragana(input);
		System.out.println(convertedHiragana);
	}

	// @Test // ひらがなの一覧を生成
	// public void testCreateHiraganaList() {
	// String[] expected = { "あ", "い", "う", "え", "お", "か", "が", "き", "ぎ", "く", "ぐ",
	// "け", "げ", "こ", "ご", "さ", "ざ",
	// "し", "じ", "す", "ず", "せ", "ぜ", "そ", "ぞ", "た", "だ", "ち", "ぢ", "っ", "つ", "づ",
	// "て", "で", "と", "ど", "な",
	// "に", "ぬ", "ね", "の", "は", "ば", "ぱ", "ひ", "び", "ぴ", "ふ", "ぶ", "ぷ", "へ", "べ",
	// "ぺ", "ほ", "ぼ", "ぽ", "ま",
	// "み", "む", "め", "も", "ゃ", "や", "ゅ", "ゆ", "ょ", "よ", "ら", "り", "る", "れ", "ろ",
	// "ゎ", "わ", "ゐ", "ゑ", "を",
	// "ん", "ゔ", "ゕ", "ゖ" };
	// String[] actual = JapaneseConverter.createHiraganaList();
	// // actualの一覧を見る
	// for (String str : actual) {
	// System.out.println(str);
	// }
	// assertArrayEquals(expected, actual);
	// }

	// @Test // カタカナの一覧を生成
	// public void testCreateKatakanaList() {
	// String[] expected = { "ア", "イ", "ウ", "エ", "オ", "カ", "ガ", "キ", "ギ", "ク", "グ",
	// "ケ", "ゲ", "コ", "ゴ", "サ", "ザ",
	// "シ", "ジ", "ス", "ズ", "セ", "ゼ", "ソ", "ゾ", "タ", "ダ", "チ", "ヂ", "ッ", "ツ", "ヅ",
	// "テ", "デ", "ト", "ド", "ナ",
	// "ニ", "ヌ", "ネ", "ノ", "ハ", "バ", "パ", "ヒ", "ビ", "ピ", "フ", "ブ", "プ", "ヘ", "ベ",
	// "ペ", "ホ", "ボ", "ポ", "マ",
	// "ミ", "ム", "メ", "モ", "ャ", "ヤ", "ュ", "ユ", "ョ", "ヨ", "ラ", "リ", "ル", "レ", "ロ",
	// "ヮ", "ワ", "ヰ", "ヱ", "ヲ",
	// "ン", "ヴ", "ヵ", "ヶ", "ヷ", "ヸ", "ヹ", "ヺ" };
	// String[] actual = JapaneseConverter.createKatakanaList();
	// // expected と actualを一覧で見る
	// for (int i = 0; i < expected.length; i++) {
	// System.out.println(expected[i] + " " + actual[i]);
	// }

	// assertArrayEquals(expected, actual);
	// }
}