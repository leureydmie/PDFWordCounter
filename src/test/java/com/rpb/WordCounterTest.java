package com.rpb;

import org.junit.Assert;
import org.junit.Test;

import com.rpb.WordCounter;

public class WordCounterTest {

	public static final String FILENAME = "test/test1.pdf";
	public static final int ACTUAL_WORD_COUNT = 846;
	
	@Test
	public void wordCountTest() {
		Assert.assertTrue(WordCounter.countWords(FILENAME) == ACTUAL_WORD_COUNT);
	}
}
