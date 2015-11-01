package edu.nyu.pa.sz1288;

import java.util.HashSet;
import java.util.Set;

public class Cluster {
	private Article centroid;
	private Set<Article> instances;
	
	public Cluster(Article article) {
		centroid = article;
		instances = new HashSet<Article>();
		instances.add(article);
	}
	
	public boolean addInstance(Article instance) {
		return instances.add(instance);
	}
	
	public void updateCentroid() {
		int len = instances.iterator().next().getWordVector().length;
		double[] wordVector = new double[len];
		for(int i=0; i<len; i++) {
			for(Article instance : instances) {
				wordVector[i] += instance.getWordVector()[i];
			}
			wordVector[i] /= instances.size();
		}
		centroid = new Article("centroid", wordVector);
	}
	
	public int size() {
		return instances.size();
	}
	
	public Article getCentroid() {
		return centroid;
	}
	
	public void resetInstances() {
		instances.clear();
	}
	
	public Set<Article> getInstances() {
		return instances;
	}

	@Override
	public String toString() {
		String res = "Contains " + size() + " instances :\n";
		res += instances.toString();
		return res;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Cluster)) {
			return false;
		}
		Cluster other = (Cluster) obj;
		return instances.equals(other.getInstances());
	}

}
