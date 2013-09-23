package biz.pavonis.hexameter;

import static junit.framework.Assert.assertNotNull;

import org.junit.Test;

import biz.pavonis.hexameter.api.HexagonalGridLayout;


public class HexagonalGridLayoutTest {

	private HexagonalGridLayout target;

	@Test
	public void testHexagonalStrategy() {
		target = HexagonalGridLayout.HEXAGONAL;
		assertNotNull(target.getGridLayoutStrategy());
	}

	@Test
	public void testTriangularStrategy() {
		target = HexagonalGridLayout.TRIANGULAR;
		assertNotNull(target.getGridLayoutStrategy());
	}

	@Test
	public void testRStrategy() {
		target = HexagonalGridLayout.RECTANGULAR;
		assertNotNull(target.getGridLayoutStrategy());
	}

}
