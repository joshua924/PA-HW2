package edu.nyu.pa.sz1288;

import java.util.List;

import edu.nyu.pa.sz1288.clustering.Clustering;
import edu.nyu.pa.sz1288.clustering.FlockClustering;
import edu.nyu.pa.sz1288.clustering.KMeansClustering;
import edu.nyu.pa.sz1288.io.ArticleReader;

public class ClusteringRunner {
	public static void main(String args[]) throws Exception {
		String filePath = args[0];
		String stopWordFile = args[1];
		int distanceMeasure = Integer.valueOf(args[3]);
		List<Article> articles = ArticleReader.createArticles(filePath, stopWordFile);
		System.out.println("The data is also stored at " + ArticleReader.dumpWordVectorToFile(articles));
		
		Clustering clustering = getClustering(Integer.valueOf(args[2]));
		List<Cluster> clusters = clustering.getClusters(articles, distanceMeasure);
		//clustering.showClusters(clusters);
	}

	private static Clustering getClustering(Integer v) {
		return v == 1 ? new KMeansClustering() : new FlockClustering();
	}
}
