package biz.pavonis.hexameter.api;

import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import biz.pavonis.hexameter.internal.impl.layoutstrategy.CustomGridLayoutStrategy;
import biz.pavonis.hexameter.internal.impl.layoutstrategy.HexagonalGridLayoutStrategy;
import biz.pavonis.hexameter.internal.impl.layoutstrategy.RectangularGridLayoutStrategy;
import biz.pavonis.hexameter.internal.impl.layoutstrategy.TrapezoidGridLayoutStrategy;
import biz.pavonis.hexameter.internal.impl.layoutstrategy.TriangularGridLayoutStrategy;
import biz.pavonis.hexameter.testutils.EnumTest;
import biz.pavonis.hexameter.testutils.TestUtils;

public class HexagonalGridLayoutTest extends EnumTest {

	@Test
	public void testEnum() {
		TestUtils.superficialEnumCodeCoverage(HexagonalGridLayout.class);
	}

	@Test
	public void testGetGridLayoutStrategyCustom() {
		assertTrue(HexagonalGridLayout.CUSTOM.getGridLayoutStrategy() instanceof CustomGridLayoutStrategy);
	}

	@Test
	public void testGetGridLayoutStrategyHexagonal() {
		assertTrue(HexagonalGridLayout.HEXAGONAL.getGridLayoutStrategy() instanceof HexagonalGridLayoutStrategy);
	}

	@Test
	public void testGetGridLayoutStrategyRectangular() {
		assertTrue(HexagonalGridLayout.RECTANGULAR.getGridLayoutStrategy() instanceof RectangularGridLayoutStrategy);
	}

	@Test
	public void testGetGridLayoutStrategyRhombus() {
		assertTrue(HexagonalGridLayout.TRAPEZOID.getGridLayoutStrategy() instanceof TrapezoidGridLayoutStrategy);
	}

	@Test
	public void testGetGridLayoutStrategyTriangular() {
		assertTrue(HexagonalGridLayout.TRIANGULAR.getGridLayoutStrategy() instanceof TriangularGridLayoutStrategy);
	}

}
