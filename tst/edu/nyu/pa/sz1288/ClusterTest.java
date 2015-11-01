package edu.nyu.pa.sz1288;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ClusterTest {
	private Article a1, a2;
	private Cluster cluster;
	
	@Before
	public void setUp() {
		a1 = new Article("1", new double[]{1.0, 2.0});
		a2 = new Article("2", new double[]{1.0, 4.0});
	}
	
	@Test
	public void testAdd() throws Exception {
		cluster = new Cluster(a1);
		assertEquals(1, cluster.size());
		cluster.addInstance(a2);
		assertEquals(2, cluster.size());
	}
	
	@Test
	public void testUpdateCentroid() throws Exception {
		cluster = new Cluster(a1);
		assertEquals(a1, cluster.getCentroid());
		cluster.addInstance(a2);
		cluster.updateCentroid();
		assertEquals(new Article("fake", new double[]{1.0, 3.0}), cluster.getCentroid());
	}

}
