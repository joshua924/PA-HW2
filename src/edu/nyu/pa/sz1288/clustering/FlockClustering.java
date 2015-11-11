package edu.nyu.pa.sz1288.clustering;

import java.util.ArrayList;
import java.util.List;

import edu.nyu.pa.sz1288.Article;
import edu.nyu.pa.sz1288.Cluster;
import edu.nyu.pa.sz1288.SpatialArticle;
import edu.nyu.pa.sz1288.util.Pair;

public class FlockClustering implements Clustering {
	private static final int ITERATION = 20;
	private static final double d1 = 5;
	private static final double d2 = 2;
	
	public List<Cluster> getClusters(List<? extends Article> instances, int distanceMeasure) {
		List<SpatialArticle> spatialInstances = new ArrayList<>();
		for(Article ar : instances) {
			spatialInstances.add(new SpatialArticle(ar));
		}
		
		// initialization
		int size = spatialInstances.size();
		double[][] similarity = new double[size][size];
		Pair[] speed = new Pair[size];
		Pair[] newSpeed = new Pair[size];
		for(int i=0; i<size; i++) {
			for(int j=i+1; j<size; j++) {
				double val = SpatialArticle.distance(spatialInstances.get(i), spatialInstances.get(j));
				similarity[i][j] = val;
				similarity[j][i] = val;
			}
			speed[i] = new Pair(0.0, 0.0);
			newSpeed[i] = new Pair(0.0, 0.0);
		}
		initPosition(spatialInstances);
		
		// iterations
		for(int v=0; v<ITERATION; v++) {
			System.out.println("Iteration " + (v + 1) + " ..");
			SpatialArticle.printSpace(spatialInstances);
			speed = newSpeed;
			newSpeed = new Pair[size];
			for (int i=0; i<size; i++) {
				SpatialArticle spatialArticle = spatialInstances.get(i);
				Pair totalMove = new Pair(0.0, 0.0);
				totalMove.add(getAlignmentShift(i, spatialInstances, speed));
				totalMove.add(getSeparationShift(i, spatialInstances, speed));
				totalMove.add(getCohesionShift(i, spatialInstances, speed));
				totalMove.add(Pair.multiply(getSimAndDissim(i, spatialInstances, similarity), 4));
				totalMove = Pair.multiply(totalMove, 1.4);
				newSpeed[i] = totalMove;
				spatialArticle.move(totalMove);
			}
		}
		return null;
	}

	private Pair getSimAndDissim(int i, List<SpatialArticle> spatialInstances, double[][] similarity) {
		Pair momentum = new Pair(0.0, 0.0);
		SpatialArticle article = spatialInstances.get(i);
		for(int k=0; k<spatialInstances.size(); k++) {
			if(k != i) {
				double spatialDistance = SpatialArticle.getSpatialDistance(spatialInstances.get(i), spatialInstances.get(k));
				Pair destine = spatialInstances.get(k).getPosition();
				Pair attract = Pair.diff(destine, article.getPosition());
				Pair repel = Pair.diff(article.getPosition(), destine);
				momentum.add(Pair.multiply(attract, similarity[i][k] * spatialDistance));
				momentum.add(Pair.divide(repel, 1 / (similarity[i][k] * spatialDistance)));
			}
		}
		return momentum;
	}

	private static Pair getAlignmentShift(int i, List<SpatialArticle> spatialInstances, Pair[] speed) {
		SpatialArticle article = spatialInstances.get(i);
		Pair averageSpeed = new Pair(0.0, 0.0);
		int count = 0;
		for(int k=0; k<spatialInstances.size(); k++) {
			double spatialDistance = SpatialArticle.getSpatialDistance(article, spatialInstances.get(k));
			if(k != i && spatialDistance <= d1 && spatialDistance > d2) {
				count++;
				averageSpeed.add(speed[k]);
			}
		}
		return Pair.divide(averageSpeed, count);
	}
	
	private static Pair getSeparationShift(int i, List<SpatialArticle> spatialInstances, Pair[] speed) {
		Pair momentum = new Pair(0.0, 0.0);
		SpatialArticle article = spatialInstances.get(i);
		for(int k=0; k<spatialInstances.size(); k++) {
			double distance = SpatialArticle.getSpatialDistance(article, spatialInstances.get(k));
			if(k != i && distance <= d2) {
				momentum.add(Pair.divide(Pair.sum(speed[i], speed[k]), distance));
			}
		}
		return momentum;
	}
	
	private static Pair getCohesionShift(int i, List<SpatialArticle> spatialInstances, Pair[] speed) {
		SpatialArticle article = spatialInstances.get(i);
		Pair averageSpeed = new Pair(0.0, 0.0);
		int count = 0;
		for(int k=0; k<spatialInstances.size(); k++) {
			double spatialDistance = SpatialArticle.getSpatialDistance(article, spatialInstances.get(k));
			if(k != i && spatialDistance <= d1 && spatialDistance > d2) {
				count++;
				averageSpeed.add(speed[k]);
			}
		}
		return Pair.divide(averageSpeed, count);
	}

	private void initPosition(List<SpatialArticle> spatialInstances) {
		int i=0, j=0;
		for (SpatialArticle spatialArticle : spatialInstances) {
			spatialArticle.setPosition(new Pair(i++ % 4, j++ % 5));
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
