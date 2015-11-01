package edu.nyu.pa.sz1288;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ArticleReader {
	private static Map<String, Integer> _dict = new HashMap<>();
	
	public static List<Article> createArticles(String filePath, String stopWordFile) {
		List<File> fileList = new ArrayList<File>();
		List<Article> articles = new ArrayList<>();
		File root = new File(filePath);
		if(!root.isDirectory()) {
			throw new IllegalArgumentException("Please input a directory");
		}
		exploreFolder(root, fileList);
		try {
			Stopword sw = new Stopword(stopWordFile);
			// first round to get all non-stop words
			int wordIndex = 0;
			System.out.println("Creating dictionary ..");
			for (File file : fileList) {
				Scanner sc = new Scanner(file);
				String line;
				while(sc.hasNextLine()) {
					line = sc.nextLine();
					if(!line.equals("")) {
						line = line.replaceAll("[^A-Za-z0-9 ]", " ").toLowerCase();
						String[] tokens = line.split(" ");
						for(String token : tokens) {
							if(!sw.isStopWord(token) && !_dict.containsKey(token)) {
								_dict.put(token, wordIndex);
								wordIndex++;
							}
						}
					}
				}
				sc.close();
			}
			
			// second round create articles
			System.out.println("Creating Articles ..");
			for (File file : fileList) {
				Scanner sc = new Scanner(file);
				double[] wordVector = new double[wordIndex];
				String line;
				while(sc.hasNextLine()) {
					line = sc.nextLine();
					if(!line.equals("")) {
						line = line.replaceAll("[^A-Za-z0-9 ]", " ").toLowerCase();
						String[] tokens = line.split(" ");
						for(String token : tokens) {
							if(!sw.isStopWord(token)) {
								wordVector[_dict.get(token)]++;
							}
						}
					}
				}
				articles.add(new Article(file.getName(), wordVector));
				sc.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return articles;
	}
	
	private static void exploreFolder(File file, List<File> fileList) {
		for(File f : file.listFiles()) {
			if(f.isDirectory()) {
				exploreFolder(f, fileList);
			} else {
				if(!f.isHidden()) {
					fileList.add(f);
				}
			}
		}
	}
	
//	public static void main(String args[]) {
//		ArticleReader.createArticles("articles", "stopwords.txt");
//		System.out.println(ArticleReader._dict);
//	}
}
