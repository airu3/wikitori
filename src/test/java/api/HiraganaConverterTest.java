package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HiraganaConverterTest {

	@Test
	public void testConvertToHiragana() {
		String inputWord = "漢字";
		String expectedHiragana = "かんじ";
		String convertedHiragana = HiraganaConverter.convertToHiragana(inputWord);
		assertEquals(expectedHiragana, convertedHiragana);
	}

	@Test
	public void testConvertToHiraganaWithEmptyInput() {
		String inputWord = "";
		String convertedHiragana = HiraganaConverter.convertToHiragana(inputWord);
		System.out.println("convertedHiragana: " + convertedHiragana);
	}

	@Test
	public void testConvertToHiraganaWithNullInput() {
		String convertedHiragana = HiraganaConverter.convertToHiragana(null);
		System.out.println("convertedHiragana: " + convertedHiragana);
	}
}