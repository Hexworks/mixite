package org.hexworks.mixite.core.api

import org.hexworks.mixite.core.api.HexagonalGridLayout.*
import org.hexworks.mixite.core.internal.impl.layoutstrategy.HexagonalGridLayoutStrategy
import org.hexworks.mixite.core.internal.impl.layoutstrategy.RectangularGridLayoutStrategy
import org.hexworks.mixite.core.internal.impl.layoutstrategy.TrapezoidGridLayoutStrategy
import org.hexworks.mixite.core.internal.impl.layoutstrategy.TriangularGridLayoutStrategy
import kotlin.test.Test
import kotlin.test.assertTrue

class HexagonalGridLayoutTest {

    @Test
    fun shouldBeHexagonalGridLayoutWhenGetGridLayoutStrategyFromHexagonalIsCalled() {
        assertTrue(HEXAGONAL.gridLayoutStrategy is HexagonalGridLayoutStrategy)
    }

    @Test
    fun shouldBeRectangularGridLayoutWhenGetGridLayoutStrategyFromRectangularIsCalled() {
        assertTrue(RECTANGULAR.gridLayoutStrategy is RectangularGridLayoutStrategy)
    }

    @Test
    fun shouldBeTrapezoidGridLayoutWhenGetGridLayoutStrategyFromTrapezoidIsCalled() {
        assertTrue(TRAPEZOID.gridLayoutStrategy is TrapezoidGridLayoutStrategy)
    }

    @Test
    fun shouldBeTriangularGridLayoutWhenGetGridLayoutStrategyFromTriangularIsCalled() {
        assertTrue(TRIANGULAR.gridLayoutStrategy is TriangularGridLayoutStrategy)
    }

}
