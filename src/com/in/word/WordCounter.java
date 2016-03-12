package com.in.word;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCounter extends Thread {
	public String fileName;
	BufferedReader buffer = null;
	ArrayList<String> wordList = new ArrayList<String>();
	ArrayList<String> allowedWords = new ArrayList<String>();
	String ipRegex = "[^\\x41-\\x5a | ^\\x61-\\x7a | ^\\x21-\\x22 | ^\\x26-\\x29 | ^\\x2c-\\x2e | ^\\x3a-\\x3b | ^\\x3f | ^\\x5b | ^\\x5d | ^\\x7b-\\x7d]";
	int count = 0;
	Pattern pattern = Pattern.compile(ipRegex);

	public WordCounter(String name) {
		fileName = name;
	}

	public void run() {
		try {
			buffer = readFile(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		print(countWords(buffer));

	}

	public BufferedReader readFile(String fileName)
			throws FileNotFoundException {

		BufferedReader buffer = null;
		buffer = new BufferedReader(new FileReader(fileName));
		return buffer;
	}

	public int countWords(BufferedReader buffer) {
		String read;
		try {
			while ((read = buffer.readLine()) != null) {
				String[] myWords = read.replaceAll("\\s+", " ").split(" ");
				for (int i = 0; i < myWords.length; i++) {
					Matcher matcher = pattern.matcher(myWords[i]);
					if (matcher.find()) {
						continue;
					} else {
						count = 0;
						count = myWords[i].length()
								- myWords[i]
										.replaceAll(
												"[\\x21 | \\x26-\\x27 | \\x2c-\\x2e | \\x3a-\\x3b | \\x3f ]",
												"").length();
						if (count > 1) {
							continue;
						} else {
							if(myWords[i].length()==1 && myWords[i].matches("[\\x21-\\x22 | \\x26-\\x29 | \\x2c-\\x2e | \\x3a-\\x3b | \\x3f | \\x5b | \\x5d | \\x7b-\\x7d]")){
							} else{
								wordList.add(myWords[i]);
							}
							
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return wordList.size();
	}

	public void print(int wordCount) {
		System.out.println("File Name: " + fileName + "\n\tTotal words are: "
				+ wordCount + "\n\tIgnoring special utf 8 character"
				+ "\n*************************************");
	}

}
