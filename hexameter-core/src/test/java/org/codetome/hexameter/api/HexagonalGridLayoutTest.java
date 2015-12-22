package org.codetome.hexameter.api;

import static junit.framework.Assert.assertTrue;
import static org.codetome.hexameter.api.HexagonalGridLayout.HEXAGONAL;
import static org.codetome.hexameter.api.HexagonalGridLayout.RECTANGULAR;
import static org.codetome.hexameter.api.HexagonalGridLayout.TRAPEZOID;
import static org.codetome.hexameter.api.HexagonalGridLayout.TRIANGULAR;
import static org.codetome.hexameter.testutils.TestUtils.superficialEnumCodeCoverage;

import org.codetome.hexameter.internal.impl.layoutstrategy.HexagonalGridLayoutStrategy;
import org.codetome.hexameter.internal.impl.layoutstrategy.RectangularGridLayoutStrategy;
import org.codetome.hexameter.internal.impl.layoutstrategy.TrapezoidGridLayoutStrategy;
import org.codetome.hexameter.internal.impl.layoutstrategy.TriangularGridLayoutStrategy;
import org.codetome.hexameter.testutils.EnumTest;
import org.junit.Test;

public class HexagonalGridLayoutTest extends EnumTest {

	@Override
    @Test
	public void testEnum() {
		superficialEnumCodeCoverage(HexagonalGridLayout.class);
	}

	@Test
	public void shouldBeHexagonalGridLayoutWhenGetGridLayoutStrategyFromHexagonalIsCalled() {
		assertTrue(HEXAGONAL.getGridLayoutStrategy() instanceof HexagonalGridLayoutStrategy);
	}

	@Test
	public void shouldBeRectangularGridLayoutWhenGetGridLayoutStrategyFromRectangularIsCalled() {
		assertTrue(RECTANGULAR.getGridLayoutStrategy() instanceof RectangularGridLayoutStrategy);
	}

	@Test
	public void shouldBeTrapezoidGridLayoutWhenGetGridLayoutStrategyFromTrapezoidIsCalled() {
		assertTrue(TRAPEZOID.getGridLayoutStrategy() instanceof TrapezoidGridLayoutStrategy);
	}

	@Test
	public void shouldBeTriangularGridLayoutWhenGetGridLayoutStrategyFromTriangularIsCalled() {
		assertTrue(TRIANGULAR.getGridLayoutStrategy() instanceof TriangularGridLayoutStrategy);
	}

}
