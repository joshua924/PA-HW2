package edu.nyu.pa.sz1288;

public class Article {
	private static final String[] MEASURES = {"Invalid", "Euclidean Distance", "Cosine Distance", "Min Hash Distance"};
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
			return cosineDistance(a1, a2);
		} else if (distanceMeasure == 3) {
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
	
	protected static double cosineDistance(Article a1, Article a2) {
		int len = Math.min(a1.wordVector.length, a2.wordVector.length);
		double res = 0.0;
		for(int i=0; i<len; i++) {
			res += a1.getWordVector()[i] * a2.getWordVector()[i];
		}
		return 1 - res / (a1.getWordVectorLength() * a2.getWordVectorLength());
	}
	
	private static double minHashDistance(Article a1, Article a2) {
		int len = Math.min(a1.wordVector.length, a2.wordVector.length);
		return jaccardDistance(a1.getWordVector(), a2.getWordVector(), 0, len);
	}
	
	static double jaccardDistance(double[] l1, double[] l2, int s, int e) {
		double union = 0.0, intersect = 0.0;
		for(int i=s; i<e; i++) {
			if(l1[i] != 0 && l2[i] != 0) {
				double sum = l1[i] * l2[i];
				union += sum;
				intersect += 1;
			} else if(l1[i] != 0 || l2[i] != 0) {
				union += 1;
			}
		}
		return union == 0.0 ? 1 : (1 - intersect / union);
	}
	
	private double getWordVectorLength() {
		double res = 0.0;
		for(double each : wordVector) {
			res += Math.pow(each, 2.0);
		}
		return Math.sqrt(res);
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
	
	public static String getMeasureName(int measure) {
		if(measure > 3) {
			throw new IllegalArgumentException("Not Implemented !");
		}
		return MEASURES[measure];
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
