package com.in.word;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;

import org.hamcrest.core.Is;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runners.Parameterized.Parameter;

public class WordCountTest {
	WordCounter obj1;
	BufferedReader buffer = null;

	@Parameter
	String filename = "file1.txt";
	String read;

	@Before
	public void setUp() throws Exception {
		obj1 = new WordCounter(filename);
		buffer = obj1.readFile(filename);
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testFile() throws Exception {
		// The filename should be of String type
		assertSame("filename is of String type", "java.lang.String",
				((Object) filename).getClass().getName());

		// the length of filename should be 9
		assertEquals("the length of filename", 9, filename.length());

		// the file should exist
		File f = new File(filename);
		assertTrue(f.exists());

		// the content of file shouldn't be empty
		assertNotNull(buffer.readLine());

		// cannot write into the file
		assertTrue(f.canWrite());
	}

	@Test(timeout = 1000)
	// the test will fail after 1000 milliseconds
	public void testCount() throws Exception {

		// the total count of words should not be null
		assertNotNull(obj1.countWords(buffer));

		// Should return True for the data type of count to be integer
		assertTrue(Integer.class.isInstance(obj1.countWords(buffer)));

		// the total count of words should be 11
		assertEquals("the total count of words", 11, obj1.countWords(buffer));

	}

	@Test
	public void testWordBoundaries() {
		// Check wordList contains non-english characters
		for (int i = 0; i < obj1.wordList.size(); i++) {
			for (int j = 0; j < obj1.wordList.get(i).length(); j++) {
				char c = obj1.wordList.get(i).charAt(j);

				// checks if there is any word excluding ascii
				assertNotEquals("[^\\p{ASCII} | \\s+]", obj1.wordList.get(i)
						.charAt(j));
				// check if there is any punctuation in wordlist
				assertEquals("[^\\p{Punct}]", obj1.wordList.get(i));

				// check if the wordlist consists of digit
				assertFalse(Character.isDigit(c));

				// check if there is any whitespace in wordlist (" ", \n)
				assertFalse(Character.isWhitespace(c));

				// check if the wordlist consists of lowercase and uppercase
				assertTrue(Character.isLowerCase(c));
				assertTrue(Character.isUpperCase(c));

			}
		}
	}

	@Test
	public void testContainedWords() {
		obj1.countWords(buffer);

		// The count shouldn't count invalid words
		assertFalse(obj1.wordList.contains(""));

		// check for valid word
		assertTrue(obj1.wordList.contains("my"));

		// check for white space
		assertFalse(obj1.wordList.contains("\\s+"));

		// check for number
		assertFalse(obj1.wordList.contains("1"));

		// check for punctuation
		assertFalse(obj1.wordList.contains("!"));

	}

}
