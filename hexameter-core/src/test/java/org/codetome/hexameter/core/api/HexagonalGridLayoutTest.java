package org.codetome.hexameter.core.api;

import org.codetome.hexameter.core.internal.impl.layoutstrategy.HexagonalGridLayoutStrategy;
import org.codetome.hexameter.core.internal.impl.layoutstrategy.RectangularGridLayoutStrategy;
import org.codetome.hexameter.core.internal.impl.layoutstrategy.TrapezoidGridLayoutStrategy;
import org.codetome.hexameter.core.internal.impl.layoutstrategy.TriangularGridLayoutStrategy;
import org.codetome.hexameter.core.testutils.EnumTest;
import org.codetome.hexameter.core.testutils.TestUtils;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.HEXAGONAL;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.RECTANGULAR;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.TRAPEZOID;
import static org.codetome.hexameter.core.api.HexagonalGridLayout.TRIANGULAR;

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

}
