package edu.nyu.pa.sz1288.clustering;

import java.util.ArrayList;
import java.util.List;

import edu.nyu.pa.sz1288.Article;
import edu.nyu.pa.sz1288.Cluster;

public class KMeansClustering implements Clustering {
	private static final int NUM_OF_CLUSTERS = 4;

	@Override
	public List<Cluster> getClusters(List<? extends Article> instances, int distanceMeasure) {
		List<Cluster> curr = new ArrayList<Cluster>(NUM_OF_CLUSTERS);
		System.out.println("Clutering using " + Article.getMeasureName(distanceMeasure) + " ..");
		
		// initialization
		for(int i=0; i<NUM_OF_CLUSTERS; i++) {
			Cluster cluster = new Cluster(instances.get(i * instances.size() / NUM_OF_CLUSTERS));
			curr.add(cluster);
		}
		
		// iterations
		List<Cluster> pre = new ArrayList<Cluster>();
		int i = 1;
		while(!pre.containsAll(curr)) {
			System.out.println("Iteration " + i++ + " ..");
			pre = new ArrayList<Cluster>(curr);
			clearAllClusterInstances(curr);
			for(Article article : instances) {
				Cluster minCluster = null;
				double minDistance = Double.MAX_VALUE;
				for(Cluster cluster : curr) {
					double distance = Article.distance(article, cluster, distanceMeasure);
					if(distance < minDistance) {
						minCluster = cluster;
						minDistance = distance;
					}
				}
				minCluster.addInstance(article);
			}
			updateAllClusters(curr);
		}
		System.out.println("Clustering finished.");
		return curr;
	}

	private void updateAllClusters(List<Cluster> curr) {
		for(Cluster cluster : curr) {
			cluster.updateCentroid();
		}
	}

	private void clearAllClusterInstances(List<Cluster> curr) {
		for(Cluster cluster : curr) {
			cluster.resetInstances();
		}
	}

	@Override
	public void showClusters(List<Cluster> clusters) {
		System.out.println("-------------------------------------------------------------");
		for(int i=0; i < clusters.size(); i++) {
			System.out.print("Cluster " + i + ": ");
			System.out.println(clusters.get(i));
			System.out.println("-------------------------------------------------------------");
		}
	}
}
