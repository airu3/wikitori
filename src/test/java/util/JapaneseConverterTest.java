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
		assertEquals("a", convertedHiragana);
	}
}