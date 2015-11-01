package edu.nyu.pa.sz1288;

import java.util.Random;

public class Article {
	private static final int NUM_OF_HASHING = 400;
	private String title;
	private double[] wordVector;

	public Article(String title, double[] wordVector) {
		this.title = title;
		this.wordVector = wordVector;
	}
	
	public static double distance(Article a1, Cluster c, int distanceMeasure) {
		if(c == null) return Double.MAX_VALUE;
		Article a2 = c.getCentroid();
		if(distanceMeasure == 1) {
			return euclideanDistance(a1, a2);
		} else if(distanceMeasure == 2) {
			return minHashDistance(a1, a2);
		} else throw new IllegalArgumentException("Not Implemented !");
	}

	private static double euclideanDistance(Article a1, Article a2) {
		int len = Math.min(a1.wordVector.length, a2.wordVector.length);
		double res = 0.0;
		for(int i=0; i<len; i++) {
			res += Math.pow(a1.wordVector[i] - a2.wordVector[i], 2.0);
		}
		return Math.sqrt(res);
	}
	
	private static double minHashDistance(Article a1, Article a2) {
		int len = Math.min(a1.wordVector.length, a2.wordVector.length);
		double res = 0.0;
		int interval = len / NUM_OF_HASHING;
		Random r = new Random();
		for(int i=0; i < NUM_OF_HASHING; i++) {
			int start = r.nextInt(len - interval);
			res += jaccardDistance(a1.getWordVector(), a2.getWordVector(), start, start + interval);
		}
		return res / NUM_OF_HASHING;
	}
	
	static double jaccardDistance(double[] l1, double[] l2, int s, int e) {
		double union = 0.0, intersect = 0.0;
		for(int i=s; i<e; i++) {
			if(l1[i] != 0 && l2[i] != 0) {
				union += 1.0;
				intersect += 1.0;
			} else if(l1[i] != 0 || l2[i] != 0) {
				union += 1.0;
			}
		}
		return 1 - intersect / union;
	}
	
	@Override
	public String toString() {
		return title;
	}

	public String getTitle() {
		return title;
	}

	public double[] getWordVector() {
		return wordVector;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Article)) {
			return false;
		}
		Article other = (Article) obj;
		for(int i=0; i<wordVector.length; i++) {
			if(wordVector[i] != other.wordVector[i])
				return false;
		}
		return true;
	}
	
}
