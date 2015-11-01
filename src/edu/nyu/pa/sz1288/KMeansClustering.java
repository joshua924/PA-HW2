package edu.nyu.pa.sz1288;

import java.util.ArrayList;
import java.util.List;

public class KMeansClustering implements Clustering {
	private static final int NUM_OF_CLUSTERS = 4;

	@Override
	public List<Cluster> getClusters(List<Article> instances, int distanceMeasure) {
		List<Cluster> curr = new ArrayList<Cluster>(NUM_OF_CLUSTERS);
		
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
					if(Article.distance(article, cluster, distanceMeasure) < minDistance) {
						minCluster = cluster;
					}
				}
				minCluster.addInstance(article);
			}
			for (Cluster cluster2 : curr) {
				System.out.println(cluster2.size());
			}
			updateAllClusters(curr);
		}
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
