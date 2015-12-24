package org.codetome.hexameter.api;

import org.codetome.hexameter.internal.impl.layoutstrategy.*;
import org.codetome.hexameter.testutils.EnumTest;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.codetome.hexameter.api.HexagonalGridLayout.*;
import static org.codetome.hexameter.testutils.TestUtils.superficialEnumCodeCoverage;

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

	@Test
	public void shouldBeEmptyrGridLayoutWhenGetGridLayoutStrategyFromEmptyIsCalled() {
		assertTrue(EMPTY.getGridLayoutStrategy() instanceof EmptyGridLayoutStrategy);
	}

}
