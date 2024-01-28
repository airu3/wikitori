package util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KanjiToHiraganaConverterTest {

	@Test
	public void testConvertToHiragana() {
		String inputWord = "漢字";
		String expectedHiragana = "かんじ";
		String convertedHiragana = KanjiToHiraganaConverter.convertToHiragana(inputWord);
		assertEquals(expectedHiragana, convertedHiragana);
	}

	@Test
	public void testConvertToHiraganaWithEmptyInput() {
		String inputWord = "";
		String expectedHiragana = "";
		String convertedHiragana = KanjiToHiraganaConverter.convertToHiragana(inputWord);
		assertEquals(expectedHiragana, convertedHiragana);
	}

	@Test
	public void testConvertToHiraganaWithNullInput() {
		String inputWord = null;
		String convertedHiragana = KanjiToHiraganaConverter.convertToHiragana(inputWord);
		assertNull(convertedHiragana);
	}
}