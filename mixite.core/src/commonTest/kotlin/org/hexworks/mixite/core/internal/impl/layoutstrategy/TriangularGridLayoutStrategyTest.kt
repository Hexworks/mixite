package org.hexworks.mixite.core.internal.impl.layoutstrategy

import org.hexworks.mixite.core.GridLayoutStrategyTestUtil.fetchDefaultBuilder
import org.hexworks.mixite.core.api.CubeCoordinate
import org.hexworks.mixite.core.api.CubeCoordinate.Companion.fromCoordinates
import org.hexworks.mixite.core.api.HexagonOrientation.FLAT_TOP
import org.hexworks.mixite.core.api.HexagonalGridBuilder
import org.hexworks.mixite.core.api.contract.SatelliteData
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TriangularGridLayoutStrategyTest {

    private lateinit var builder: HexagonalGridBuilder<out SatelliteData>
    private lateinit var target: TriangularGridLayoutStrategy

    @BeforeTest
    fun setUp() {
        builder = fetchDefaultBuilder()
        target = TriangularGridLayoutStrategy()
    }

    @Test
    fun shouldProperlyCreateHexagonsWithPointyOrientationWhenCreateHexagonsIsCalled() {
        testCoordinates(target.fetchGridCoordinates(builder).iterator())
    }

    @Test
    fun shouldProperlyCreateHexagonsWithFlatOrientationWhenCreateHexagonsIsCalled() {
        builder.setOrientation(FLAT_TOP)
        testCoordinates(target.fetchGridCoordinates(builder).iterator())
    }

    @Test
    fun shouldReturnTrueWhenCheckParametersWithOneAndOne() {
        val result = target.checkParameters(1, 1) // super: true, derived: true
        assertTrue(result)
    }

    @Test
    fun shouldReturnTrueWhenCheckParametersWithOneAndTwo() {
        val result = target.checkParameters(1, 2) // super: true, derived: false
        assertFalse(result)
    }

    @Test
    fun shouldReturnTrueWhenCheckParametersWithZeroAndZero() {
        val result = target.checkParameters(0, 0) // super: false, derived: false;
        assertFalse(result)
    }

    @Test
    fun shouldReturnTrueWhenCheckParametersWithMinusOneAndMinusOne() {
        val result = target.checkParameters(-1, -1) // super: false, derived: true;
        assertFalse(result)
    }

    private fun testCoordinates(coordIter: Iterator<CubeCoordinate>) {

        val coords = ArrayList<CubeCoordinate>()
        while (coordIter.hasNext()) {
            coords.add(coordIter.next())
        }
        assertTrue(coords.contains(fromCoordinates(0, 0)))
        assertTrue(coords.contains(fromCoordinates(1, 0)))
        assertTrue(coords.contains(fromCoordinates(2, 0)))
        assertTrue(coords.contains(fromCoordinates(0, 1)))
        assertTrue(coords.contains(fromCoordinates(1, 1)))
        assertTrue(coords.contains(fromCoordinates(0, 2)))

        assertTrue(!coords.contains(fromCoordinates(-1, 0)))
        assertTrue(!coords.contains(fromCoordinates(0, -1)))
        assertTrue(!coords.contains(fromCoordinates(1, -1)))
        assertTrue(!coords.contains(fromCoordinates(2, -1)))
        assertTrue(!coords.contains(fromCoordinates(3, -1)))
        assertTrue(!coords.contains(fromCoordinates(3, 0)))
        assertTrue(!coords.contains(fromCoordinates(2, 1)))
        assertTrue(!coords.contains(fromCoordinates(1, 2)))
        assertTrue(!coords.contains(fromCoordinates(0, 3)))
        assertTrue(!coords.contains(fromCoordinates(-1, 3)))
        assertTrue(!coords.contains(fromCoordinates(-1, 2)))
        assertTrue(!coords.contains(fromCoordinates(-1, 1)))
    }
}

