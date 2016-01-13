package org.codetome.hexameter.core.api;

import org.codetome.hexameter.core.internal.impl.layoutstrategy.*;
import org.codetome.hexameter.core.testutils.TestUtils;
import org.codetome.hexameter.core.testutils.EnumTest;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.*;

public class HexagonalGridLayoutTest extends EnumTest {

	@Override
    @Test
	public void testEnum() {
		TestUtils.superficialEnumCodeCoverage(HexagonalGridLayout.class);
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
