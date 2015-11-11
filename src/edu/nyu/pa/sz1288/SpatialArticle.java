package edu.nyu.pa.sz1288;

import java.util.List;

import edu.nyu.pa.sz1288.util.Pair;

public class SpatialArticle extends Article {
	private Pair position;
	
	public SpatialArticle(String title, double[] wordVector) {
		super(title, wordVector);
	}
	
	public SpatialArticle(Article article) {
		super(article.getTitle(), article.getWordVector());
		position = null;
	}

	public static void printSpace(List<SpatialArticle> articles) {
		for (SpatialArticle article : articles) {
			System.out.println(article.getTitle() + "," + article.getX() + "," + article.getY());
		}
	}
	
	public static double getSpatialDistance(SpatialArticle a1, SpatialArticle a2) {
		return Math.sqrt(Math.pow(a1.getX() - a2.getX(), 2) + Math.pow(a1.getY() - a2.getY(), 2));
	}
	
	private double getY() {
		return position.getY();
	}

	private double getX() {
		return position.getX();
	}

	public static double distance(SpatialArticle a1, SpatialArticle a2) {
		return 1 - cosineDistance(a1, a2);
	}

	public Pair getPosition() {
		return position;
	}
	
	public void setPosition(Pair pair) {
		position = pair;
	}
	
	public void move(Pair pair) {
		position.add(pair);
	}
}
