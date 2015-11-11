package edu.nyu.pa.sz1288.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.nyu.pa.sz1288.Article;
import edu.nyu.pa.sz1288.Stopword;

public class ArticleReader {
	private static final String FILE = "wordVector.csv";
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
			
			// second round create articles, using term frequency
			System.out.println("Creating Articles ..");
			for (File file : fileList) {
				int wordCount = 0;
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
								wordCount++;
								wordVector[_dict.get(token)]++;
							}
						}
					}
				}
				for(int i=0; i<wordVector.length; i++) {
					wordVector[i] /= wordCount;
				}
				articles.add(new Article(file.getName(), wordVector));
				sc.close();
			}
			System.out.println("done.");
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
	
	public static String dumpWordVectorToFile(List<Article> articles) throws Exception {
		File file = new File("vector", FILE);
		PrintWriter pw = new PrintWriter(file);
		String toWrite = "";
		for(int i=1; i<=_dict.size(); i++) {
			toWrite += "w-" + i + ",";
		}
		pw.write(toWrite.substring(0, toWrite.length() - 1) + "\n");
		for(Article art : articles) {
			toWrite = "";
			for(double each : art.getWordVector()) {
				toWrite += each + ",";
			}
			pw.write(toWrite.substring(0, toWrite.length() - 1) + "\n");
		}
		pw.flush(); pw.close();
		return file.getPath();
	}
}
