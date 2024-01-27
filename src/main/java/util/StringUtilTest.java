package util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class StringUtilTest {

	@Test
	public void testTrimWordFromEnd() {
		String str = "こんにちは";
		String expected = "こん";
		String regex = "は";
		String actual = StringUtil.trimWordFromEnd(str, regex);
		assertEquals(expected, actual);
	}

	@Test
	public void testExtractFirstChar() {
		String str = "こんにちは";
		String expected = "こ";
		String actual = StringUtil.extractFirstChar(str);
		assertEquals(expected, actual);
	}

	@Test
	public void testExtractLastChar() {
		String str = "こんにちは";
		String expected = "は";
		String actual = StringUtil.extractLastChar(str);
		assertEquals(expected, actual);
	}

	@Test
	public void testRemoveLastChar() {
		String str = "こんにちは";
		String expected = "こんにち";
		String actual = StringUtil.removeLastChar(str);
		assertEquals(expected, actual);
	}

	@Test
	public void testProcessJapaneseWordHiragana() {
		String inputWord = "こんにちは";
		int flag = 1;
		String[] expected = { "こ", "コ" };
		String[] actual = StringUtil.processJapaneseWord(inputWord, flag);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testProcessJapaneseWordKatakana() {
		String inputWord = "コンニチハ";
		int flag = -1;
		String[] expected = { "は", "ハ" };
		String[] actual = StringUtil.processJapaneseWord(inputWord, flag);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testProcessJapaneseWordKanji() {
		String inputWord = "今日は";
		int flag = 1;
		String[] expected = { "こ", "こ" };
		String[] actual = StringUtil.processJapaneseWord(inputWord, flag);
		assertArrayEquals(expected, actual);
	}

}