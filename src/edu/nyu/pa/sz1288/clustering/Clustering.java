package edu.nyu.pa.sz1288.clustering;

import java.util.List;

import edu.nyu.pa.sz1288.Article;
import edu.nyu.pa.sz1288.Cluster;

public interface Clustering {
	/**
	 * 
	 * @param instances
	 * @param distanceMeasure 
	 * @return a list of clusters, each of which contains a set of articles
	 */
	public List<Cluster> getClusters(List<? extends Article> instances, int distanceMeasure);
	
	/**
	 * 
	 * @param instances
	 * compute the clusters and print the internal structure
	 */
	public void showClusters(List<Cluster> cluster);
}
