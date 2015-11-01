package edu.nyu.pa.sz1288;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Stopword {
	private static Set<String> _stopWords;
	
	public Stopword(String stopWordFile) throws IOException {
		_stopWords = new HashSet<String>();
		Scanner sc = new Scanner(new File(stopWordFile));
		while(sc.hasNextLine()) {
			_stopWords.add(sc.nextLine());
		}
		_stopWords.add("");
		sc.close();
	}
	
	public boolean isStopWord(String word) {
		return _stopWords.contains(word);
	}
}
