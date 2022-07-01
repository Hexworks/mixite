package org.hexworks.mixite2.core.internal.impl.layoutstrategy

import org.hexworks.mixite2.core.GridLayoutStrategyTestUtil
import org.hexworks.mixite2.core.api.CubeCoordinate
import org.hexworks.mixite2.core.api.CubeCoordinate.Companion.fromCoordinates
import org.hexworks.mixite2.core.api.HexagonOrientation.FLAT_TOP
import org.hexworks.mixite2.core.api.HexagonalGridBuilder
import org.hexworks.mixite2.core.api.contract.SatelliteData
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class TrapezoidGridLayoutStrategyTest {

    private lateinit var builder: HexagonalGridBuilder<out SatelliteData>
    private lateinit var target: TrapezoidGridLayoutStrategy

    @BeforeTest
    fun setUp() {
        builder = GridLayoutStrategyTestUtil.fetchDefaultBuilder()
        target = TrapezoidGridLayoutStrategy()
    }

    @Test
    fun shouldProperlyCreateHexagonsWithPointyOrientationWhenCreateHexagonsIsCalled() {
        testCoordinates(target.fetchGridCoordinates(builder).iterator())
    }

    private fun testCoordinates(coordIter: Iterator<org.hexworks.mixite2.core.api.CubeCoordinate>) {
        val coords = ArrayList<org.hexworks.mixite2.core.api.CubeCoordinate>()
        while (coordIter.hasNext()) {
            coords.add(coordIter.next())
        }

        assertTrue(coords.contains(fromCoordinates(0, 0)))
        assertTrue(coords.contains(fromCoordinates(1, 0)))
        assertTrue(coords.contains(fromCoordinates(2, 0)))
        assertTrue(coords.contains(fromCoordinates(2, 1)))
        assertTrue(coords.contains(fromCoordinates(2, 2)))
        assertTrue(coords.contains(fromCoordinates(1, 2)))
        assertTrue(coords.contains(fromCoordinates(0, 2)))
        assertTrue(coords.contains(fromCoordinates(0, 1)))

        assertTrue(!coords.contains(fromCoordinates(-1, 0)))
        assertTrue(!coords.contains(fromCoordinates(0, -1)))
        assertTrue(!coords.contains(fromCoordinates(1, -1)))
        assertTrue(!coords.contains(fromCoordinates(2, -1)))
        assertTrue(!coords.contains(fromCoordinates(3, -1)))
        assertTrue(!coords.contains(fromCoordinates(3, 0)))
        assertTrue(!coords.contains(fromCoordinates(3, 1)))
        assertTrue(!coords.contains(fromCoordinates(3, 2)))
        assertTrue(!coords.contains(fromCoordinates(2, 3)))
        assertTrue(!coords.contains(fromCoordinates(1, 3)))
        assertTrue(!coords.contains(fromCoordinates(0, 3)))
        assertTrue(!coords.contains(fromCoordinates(-1, 2)))
        assertTrue(!coords.contains(fromCoordinates(-1, 1)))
    }

    @Test
    fun shouldProperlyCreateHexagonsWithFlatOrientationWhenCreateHexagonsIsCalled() {
        builder.setOrientation(FLAT_TOP)
        testCoordinates(target.fetchGridCoordinates(builder).iterator())
    }

    @Test
    fun shouldReturnTrueWhenCheckParametersIsCalled() {
        assertTrue(target.checkParameters(2, 2))
    }

}
