package edu.nyu.pa.sz1288;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ArticleTest {
	private Article a1, a2, a3;
	private Cluster c;

	@Before
	public void setUp() {
		a1 = new Article("test1", new double[]{3.0, 1.0, 0});
		a2 = new Article("test2", new double[]{0, 1.0, 4.0});
		a3 = new Article("test3", new double[]{1.0, 1.0, 4.0});
		c = new Cluster(a1);
	}

	@Test
	public void testDistance() {
		assertEquals(5.0, Article.distance(a2, c, 1), 1e-15);
		c.addInstance(a3);
		c.updateCentroid();
		assertEquals(2.0 * Math.sqrt(2.0), Article.distance(a2, c, 1), 1e-15);
	}

	@Test
	public void testJaccardDistance() throws Exception {
		assertEquals(2.0/3.0, Article.jaccardDistance(a1.getWordVector(), a2.getWordVector(), 0, 3), 1e-15);
	}
}
