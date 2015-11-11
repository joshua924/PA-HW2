package edu.nyu.pa.sz1288.util;

public class Pair {
	private double x;
	private double y;
	
	public Pair(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public void add(Pair pair) {
		setX(getX() + pair.getX());
		setY(getY() + pair.getY());
	}
	
	public static Pair divide(Pair pair, double d) {
		pair.setX(pair.getX() / d);
		pair.setY(pair.getY() / d);
		return pair;
	}
	
	public static Pair multiply(Pair pair, double d) {
		pair.setX(pair.getX() * d);
		pair.setY(pair.getY() * d);
		return pair;
	}
	
	public static Pair sum(Pair p1, Pair p2) {
		return new Pair(p1.getX() + p2.getX(), p1.getY() + p2.getY());
	}
	
	public static Pair diff(Pair p1, Pair p2) {
		return new Pair(p1.getX() - p2.getX(), p1.getY() - p2.getY());
	}
}
