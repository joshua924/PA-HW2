package edu.nyu.pa.sz1288;

import java.util.List;

public class ClusteringTester {
	public static void main(String args[]) {
		String filePath = args[0];
		String stopWordFile = args[1];
		int distanceMeasure = Integer.valueOf(args[2]);
		List<Article> articles = ArticleReader.createArticles(filePath, stopWordFile);
		
		Clustering clustering = new KMeansClustering();
		List<Cluster> clusters = clustering.getClusters(articles, distanceMeasure);
		clustering.showClusters(clusters);
	}
}
