package biz.pavonis.hexameter;

import static junit.framework.Assert.assertNotNull;

import org.junit.Test;

public class HexagonalGridLayoutTest {

	private HexagonGridLayout target;

	@Test
	public void testHexagonalStrategy() {
		target = HexagonGridLayout.HEXAGONAL;
		assertNotNull(target.getGridLayoutStrategy());
	}

	@Test
	public void testTriangularStrategy() {
		target = HexagonGridLayout.TRIANGULAR;
		assertNotNull(target.getGridLayoutStrategy());
	}

	@Test
	public void testRStrategy() {
		target = HexagonGridLayout.RECTANGULAR;
		assertNotNull(target.getGridLayoutStrategy());
	}

}
