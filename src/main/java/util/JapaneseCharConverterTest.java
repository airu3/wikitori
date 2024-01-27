package util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class JapaneseCharConverterTest {

	@Test
	public void testConvertKatakanaToHiragana() {
		String katakana = "アイウエオ";
		String expectedHiragana = "あいうえお";
		String convertedHiragana = JapaneseCharConverter.convertKatakanaToHiragana(katakana);
		assertEquals(expectedHiragana, convertedHiragana);
	}

	@Test
	public void testConvertHiraganaToKatakana() {
		String hiragana = "あいうえお";
		String expectedKatakana = "アイウエオ";
		String convertedKatakana = JapaneseCharConverter.convertHiraganaToKatakana(hiragana);
		assertEquals(expectedKatakana, convertedKatakana);
	}

	@Test
	public void testIsHiragana() {
		String hiragana = "あいうえお";
		assertTrue(JapaneseCharConverter.isHiragana(hiragana));

		String katakana = "アイウエオ";
		assertFalse(JapaneseCharConverter.isHiragana(katakana));
	}

	@Test
	public void testIsKatakana() {
		String katakana = "アイウエオ";
		assertTrue(JapaneseCharConverter.isKatakana(katakana));

		String hiragana = "あいうえお";
		assertFalse(JapaneseCharConverter.isKatakana(hiragana));
	}

	@Test
	public void testConvertHiraToKata() {
		String hiragana = "あいうえお";
		String expectedKatakana = "アイウエオ";
		String convertedKatakana = JapaneseCharConverter.convertHiraToKata(hiragana);
		assertEquals(expectedKatakana, convertedKatakana);
	}

	@Test
	public void testConvertH2K() {
		String hiragana = "あいうえお";
		String expectedKatakana = "アイウエオ";
		String convertedKatakana = JapaneseCharConverter.convertH2K(hiragana);
		assertEquals(expectedKatakana, convertedKatakana);
	}

	@Test
	public void testConvertKataToHira() {
		String katakana = "アイウエオ";
		String expectedHiragana = "あいうえお";
		String convertedHiragana = JapaneseCharConverter.convertKataToHira(katakana);
		assertEquals(expectedHiragana, convertedHiragana);
	}

	@Test
	public void testConvertK2H() {
		String katakana = "アイウエオ";
		String expectedHiragana = "あいうえお";
		String convertedHiragana = JapaneseCharConverter.convertK2H(katakana);
		assertEquals(expectedHiragana, convertedHiragana);
	}
}