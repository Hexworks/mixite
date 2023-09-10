package org.hexworks.mixite2.core.internal.impl.layoutstrategy

import org.hexworks.mixite2.core.GridLayoutStrategyTestUtil.fetchDefaultBuilder
import org.hexworks.mixite2.core.api.CubeCoordinate
import org.hexworks.mixite2.core.api.CubeCoordinate.Companion.fromCoordinates
import org.hexworks.mixite2.core.api.HexagonOrientation.FLAT_TOP
import org.hexworks.mixite2.core.api.HexagonalGridBuilder
import org.hexworks.mixite2.core.api.contract.SatelliteData
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HexagonalGridLayoutStrategyTest {

    private lateinit var target: HexagonalGridLayoutStrategy
    private lateinit var builder: HexagonalGridBuilder<out SatelliteData>

    @BeforeTest
    fun setUp() {
        builder = fetchDefaultBuilder()
        target = HexagonalGridLayoutStrategy()
    }

    @Test
    fun shouldProperlyCreateHexagonsWithPointyOrientationWhenCreateHexagonsIsCalled() {
        val coordIter = target.fetchGridCoordinates(builder).iterator()

        val coords = ArrayList<org.hexworks.mixite2.core.api.CubeCoordinate>()
        while (coordIter.hasNext()) {
            coords.add(coordIter.next())
        }

        assertTrue(coords.contains(fromCoordinates(1, 0)))
        assertTrue(coords.contains(fromCoordinates(2, 0)))
        assertTrue(coords.contains(fromCoordinates(2, 1)))
        assertTrue(coords.contains(fromCoordinates(1, 2)))
        assertTrue(coords.contains(fromCoordinates(0, 2)))
        assertTrue(coords.contains(fromCoordinates(0, 1)))

        assertTrue(!coords.contains(fromCoordinates(0, 0)))
        assertTrue(!coords.contains(fromCoordinates(1, -1)))
        assertTrue(!coords.contains(fromCoordinates(2, -1)))
        assertTrue(!coords.contains(fromCoordinates(3, -1)))
        assertTrue(!coords.contains(fromCoordinates(3, 0)))
        assertTrue(!coords.contains(fromCoordinates(3, 1)))
        assertTrue(!coords.contains(fromCoordinates(2, 2)))
        assertTrue(!coords.contains(fromCoordinates(1, 3)))
        assertTrue(!coords.contains(fromCoordinates(0, 3)))
        assertTrue(!coords.contains(fromCoordinates(-1, 3)))
        assertTrue(!coords.contains(fromCoordinates(-1, 2)))
        assertTrue(!coords.contains(fromCoordinates(-1, 1)))
    }

    @Test
    fun shouldProperlyCreateHexagonsWithPointyOrientationWhenCreateHexagonsIsCalledWithBiggerSize() {
        builder.setGridHeight(5).setGridWidth(5)
        val coordIter = target.fetchGridCoordinates(builder).iterator()

        val coords = ArrayList<org.hexworks.mixite2.core.api.CubeCoordinate>()
        while (coordIter.hasNext()) {
            coords.add(coordIter.next())
        }

        assertTrue(coords.contains(fromCoordinates(1, 0)))
        assertTrue(coords.contains(fromCoordinates(2, 0)))
        assertTrue(coords.contains(fromCoordinates(3, 0)))
        assertTrue(coords.contains(fromCoordinates(3, 1)))
        assertTrue(coords.contains(fromCoordinates(3, 2)))
        assertTrue(coords.contains(fromCoordinates(2, 3)))
        assertTrue(coords.contains(fromCoordinates(1, 4)))
        assertTrue(coords.contains(fromCoordinates(0, 4)))
        assertTrue(coords.contains(fromCoordinates(-1, 4)))
        assertTrue(coords.contains(fromCoordinates(-1, 3)))
        assertTrue(coords.contains(fromCoordinates(-1, 2)))
        assertTrue(coords.contains(fromCoordinates(0, 1)))

        assertTrue(!coords.contains(fromCoordinates(0, 0)))
        assertTrue(!coords.contains(fromCoordinates(1, -1)))
        assertTrue(!coords.contains(fromCoordinates(2, -1)))
        assertTrue(!coords.contains(fromCoordinates(3, -1)))
        assertTrue(!coords.contains(fromCoordinates(4, -1)))
        assertTrue(!coords.contains(fromCoordinates(4, 0)))
        assertTrue(!coords.contains(fromCoordinates(4, 1)))
        assertTrue(!coords.contains(fromCoordinates(4, 2)))
        assertTrue(!coords.contains(fromCoordinates(3, 3)))
        assertTrue(!coords.contains(fromCoordinates(2, 4)))
        assertTrue(!coords.contains(fromCoordinates(1, 5)))
        assertTrue(!coords.contains(fromCoordinates(0, 5)))
        assertTrue(!coords.contains(fromCoordinates(-1, 5)))
        assertTrue(!coords.contains(fromCoordinates(-2, 5)))
        assertTrue(!coords.contains(fromCoordinates(-2, 4)))
        assertTrue(!coords.contains(fromCoordinates(-2, 3)))
        assertTrue(!coords.contains(fromCoordinates(-2, 2)))
        assertTrue(!coords.contains(fromCoordinates(-1, 1)))
    }

    @Test
    fun shouldProperlyCreateHexagonsWithFlatOrientationWhenCreateHexagonsIsCalled() {
        builder.setOrientation(FLAT_TOP)
        val coordIter = target.fetchGridCoordinates(builder).iterator()

        val coords = ArrayList<org.hexworks.mixite2.core.api.CubeCoordinate>()
        while (coordIter.hasNext()) {
            coords.add(coordIter.next())
        }


        assertTrue(coords.contains(fromCoordinates(1, 0)))
        assertTrue(coords.contains(fromCoordinates(2, 0)))
        assertTrue(coords.contains(fromCoordinates(2, 1)))
        assertTrue(coords.contains(fromCoordinates(1, 2)))
        assertTrue(coords.contains(fromCoordinates(0, 2)))
        assertTrue(coords.contains(fromCoordinates(0, 1)))

        assertTrue(!coords.contains(fromCoordinates(0, 0)))
        assertTrue(!coords.contains(fromCoordinates(0, -1)))
        assertTrue(!coords.contains(fromCoordinates(2, -1)))
        assertTrue(!coords.contains(fromCoordinates(3, -1)))
        assertTrue(!coords.contains(fromCoordinates(3, 0)))
        assertTrue(!coords.contains(fromCoordinates(3, 1)))
        assertTrue(!coords.contains(fromCoordinates(2, 2)))
        assertTrue(!coords.contains(fromCoordinates(1, 3)))
        assertTrue(!coords.contains(fromCoordinates(0, 3)))
        assertTrue(!coords.contains(fromCoordinates(-1, 3)))
        assertTrue(!coords.contains(fromCoordinates(-1, 2)))
        assertTrue(!coords.contains(fromCoordinates(-1, 1)))
    }

    @Test
    fun shouldProperlyCreateHexagonsWithFlatOrientationWhenCreateHexagonsIsCalledWithBiggerSize() {
        builder.setGridHeight(5).setGridWidth(5).setOrientation(FLAT_TOP)
        val coordIter = target.fetchGridCoordinates(builder).iterator()

        val coords = ArrayList<org.hexworks.mixite2.core.api.CubeCoordinate>()
        while (coordIter.hasNext()) {
            coords.add(coordIter.next())
        }


        assertTrue(coords.contains(fromCoordinates(2, -1)))
        assertTrue(coords.contains(fromCoordinates(3, -1)))
        assertTrue(coords.contains(fromCoordinates(4, -1)))
        assertTrue(coords.contains(fromCoordinates(4, 0)))
        assertTrue(coords.contains(fromCoordinates(4, 1)))
        assertTrue(coords.contains(fromCoordinates(3, 2)))
        assertTrue(coords.contains(fromCoordinates(2, 3)))
        assertTrue(coords.contains(fromCoordinates(1, 3)))
        assertTrue(coords.contains(fromCoordinates(0, 3)))
        assertTrue(coords.contains(fromCoordinates(0, 2)))
        assertTrue(coords.contains(fromCoordinates(0, 1)))
        assertTrue(coords.contains(fromCoordinates(1, 0)))

        assertTrue(!coords.contains(fromCoordinates(0, 0)))
        assertTrue(!coords.contains(fromCoordinates(1, -1)))
        assertTrue(!coords.contains(fromCoordinates(2, -2)))
        assertTrue(!coords.contains(fromCoordinates(3, -2)))
        assertTrue(!coords.contains(fromCoordinates(4, -2)))
        assertTrue(!coords.contains(fromCoordinates(5, -2)))
        assertTrue(!coords.contains(fromCoordinates(5, -1)))
        assertTrue(!coords.contains(fromCoordinates(5, 0)))
        assertTrue(!coords.contains(fromCoordinates(5, 1)))
        assertTrue(!coords.contains(fromCoordinates(4, 2)))
        assertTrue(!coords.contains(fromCoordinates(3, 3)))
        assertTrue(!coords.contains(fromCoordinates(2, 4)))
        assertTrue(!coords.contains(fromCoordinates(1, 4)))
        assertTrue(!coords.contains(fromCoordinates(0, 4)))
        assertTrue(!coords.contains(fromCoordinates(-1, 4)))
        assertTrue(!coords.contains(fromCoordinates(-1, 3)))
        assertTrue(!coords.contains(fromCoordinates(-1, 2)))
        assertTrue(!coords.contains(fromCoordinates(-1, 1)))
    }

    @Test
    fun testCheckParameters0() {
        val result = target.checkParameters(1, 1) // super: true, derived: true
        assertTrue(result)
    }

    @Test
    fun testCheckParameters1() {
        val result = target.checkParameters(1, 2) // super: true, derived: false
        assertFalse(result)
    }

    @Test
    fun testCheckParameters2() {
        val result = target.checkParameters(2, 2) // super: true, derived: false
        assertFalse(result)
    }

    @Test
    fun testCheckParameters3() {
        val result = target.checkParameters(0, 0) // super: false, derived: false;
        assertFalse(result)
    }

    @Test
    fun testCheckParameters4() {
        val result = target.checkParameters(-1, -1) // super: false, derived: true;
        assertFalse(result)
    }

}
