package util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class StringUtilTest {

	@Test
	public void testTrimWordFromEnd() {
		String str = "こんにちは漢字検定!";
		String expected = "こんにちは漢字検定";
		String REGEX = "(?:(?!\\p{Lm})\\p{L})|\\p{N}";
		Pattern pattern = Pattern.compile(REGEX);
		String actual = StringUtil.trimWordFromEnd(str, pattern);
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
		String[] expected = { "こ", "コ" };
		String[] actual = StringUtil.processJapaneseWord(inputWord, flag);
		assertArrayEquals(expected, actual);
	}

	@Test // 最後の文字が修飾文字の場合
	public void testProcessJapaneseWordModifier() {
		String inputWord = "今日は漢字検定 !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~\r\n" + //
				"";
		int flag = 1;
		String[] expected = { "き", "キ" };
		String[] actual = StringUtil.processJapaneseWord(inputWord, flag);
		assertArrayEquals(expected, actual);
	}

	@Test // 最後の文字を漢字からひらがなに変換して抽出する
	public void testProcessJapaneseWordKanjiToHiragana() {
		String inputWord = "卵を刀で斬っちゃった幹事";
		int flag = -1;
		String[] expected = { "じ", "ジ" };
		String[] actual = StringUtil.processJapaneseWord(inputWord, flag);
		assertArrayEquals(expected, actual);
	}

}