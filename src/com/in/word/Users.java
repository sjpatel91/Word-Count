package com.in.word;

import java.util.Scanner;

public class Users {

	public static void main(String[] args) {
		String filenames;
		String[] filename;
		WordCounter wct = null;
		WordCounter wctList[] = null;
		Scanner sc = new Scanner(System.in);
		System.out
				.println("Enter file name including extension seperated by '//' :");
		System.out
				.println("Example: file1.txt//file2.txt//file3.txt//file4.txt//file5.txt");
		filenames = sc.next();
		filename = filenames.split("//");
		wctList = new WordCounter[filename.length];
		for (int i = 0; i < filename.length; i++) {
			wct = new WordCounter(filename[i]);
			wctList[i] = wct;
			wct.start();
		}

		for (WordCounter wcThread : wctList) {
			try {
				wcThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		sc.close();

	}

}
