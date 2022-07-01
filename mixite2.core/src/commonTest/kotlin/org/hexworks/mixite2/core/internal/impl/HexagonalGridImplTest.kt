package org.hexworks.mixite2.core.internal.impl

import org.hexworks.mixite2.core.api.*
import org.hexworks.mixite2.core.api.CubeCoordinate.Companion.fromCoordinates
import org.hexworks.mixite2.core.api.defaults.DefaultSatelliteData
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

/**
 * This class does not actually run its own tests! This is a base class,
 * and the subclasses each run the inherited tests.
 */
abstract class HexagonalGridImplTest {

    internal lateinit var target: HexagonalGrid<DefaultSatelliteData>
    private lateinit var builder: HexagonalGridBuilder<DefaultSatelliteData>

    abstract fun getBuilder() : HexagonalGridBuilder<DefaultSatelliteData>

    @BeforeTest
    fun setUp() {
        builder = getBuilder()
        target = builder.build()
    }

    @Test
    fun shouldReturnHexagonsInProperIterationOrderWhenGetHexagonsIsCalled() {
        val expectedCoordinates = ArrayList<org.hexworks.mixite2.core.api.CubeCoordinate>()
        val actualCoordinates = ArrayList<org.hexworks.mixite2.core.api.CubeCoordinate>()

        for (cubeCoordinate in builder.gridLayoutStrategy.fetchGridCoordinates(builder)) {
            expectedCoordinates.add(cubeCoordinate)
        }
        for (hexagon in target.hexagons) {
            actualCoordinates.add(hexagon.cubeCoordinate)
        }

        assertEquals(expectedCoordinates, actualCoordinates)
    }

    @Test
    abstract fun shouldReturnProperHexagonsWhenEachHexagonIsTestedInAGivenGrid()

    @Test
    fun shouldReturnProperHexagonsWhenGetHexagonsByAxialRangeIsCalled() {
        val expected = HashSet<Hexagon<DefaultSatelliteData>>()

        expected.add(target.getByCubeCoordinate(fromCoordinates(2, 3)).get())
        expected.add(target.getByCubeCoordinate(fromCoordinates(3, 3)).get())
        expected.add(target.getByCubeCoordinate(fromCoordinates(4, 3)).get())

        expected.add(target.getByCubeCoordinate(fromCoordinates(2, 4)).get())
        expected.add(target.getByCubeCoordinate(fromCoordinates(3, 4)).get())
        expected.add(target.getByCubeCoordinate(fromCoordinates(4, 4)).get())

        expected.add(target.getByCubeCoordinate(fromCoordinates(2, 5)).get())
        expected.add(target.getByCubeCoordinate(fromCoordinates(3, 5)).get())
        expected.add(target.getByCubeCoordinate(fromCoordinates(4, 5)).get())

        val actual = target.getHexagonsByCubeRange(fromCoordinates(GRID_X_FROM, GRID_Z_FROM), fromCoordinates(GRID_X_TO, GRID_Z_TO))
        var count = 0

        val actuals = ArrayList<Hexagon<DefaultSatelliteData>>()
        for (hex in actual) {
            actuals.add(hex)
            count++
        }
        assertEquals(expected.size, count)
        for (hex in actuals) {
            expected.remove(hex)
        }
        assertTrue(expected.isEmpty())
    }

    @Test
    fun shouldReturnProperHexagonsWhenGetHexagonsByOffsetRangeIsCalled() {
        val expected = HashSet<Hexagon<DefaultSatelliteData>>()

        expected.add(target.getByCubeCoordinate(fromCoordinates(1, 3)).get())
        expected.add(target.getByCubeCoordinate(fromCoordinates(2, 3)).get())
        expected.add(target.getByCubeCoordinate(fromCoordinates(3, 3)).get())

        expected.add(target.getByCubeCoordinate(fromCoordinates(0, 4)).get())
        expected.add(target.getByCubeCoordinate(fromCoordinates(1, 4)).get())
        expected.add(target.getByCubeCoordinate(fromCoordinates(2, 4)).get())

        expected.add(target.getByCubeCoordinate(fromCoordinates(0, 5)).get())
        expected.add(target.getByCubeCoordinate(fromCoordinates(1, 5)).get())
        expected.add(target.getByCubeCoordinate(fromCoordinates(2, 5)).get())

        val actual = target.getHexagonsByOffsetRange(GRID_X_FROM, GRID_X_TO, GRID_Z_FROM, GRID_Z_TO)
        var count = 0
        val actuals = ArrayList<Hexagon<DefaultSatelliteData>>()
        for (hex in actual) {
            actuals.add(hex)
            count++
        }
        assertEquals(expected.size, count)
        for (hex in actuals) {
            expected.remove(hex)
        }
        assertTrue(expected.isEmpty())
    }

    @Test
    fun shouldContainCoordinateWhenContainsCoorinateIsCalledWithProperParameters() {
        val gridX = 2
        val gridZ = 3
        assertTrue(target.containsCubeCoordinate(fromCoordinates(gridX, gridZ)))
    }

    @Test
    fun shouldReturnHexagonWhenGetByGridCoordinateIsCalledWithProperCoordinates() {
        val gridX = 2
        val gridZ = 3
        val hex = target.getByCubeCoordinate(fromCoordinates(gridX, gridZ))
        assertTrue(hex.isPresent)
    }

    @Test
    fun shouldBeEmptyWhenGetByGridCoordinateIsCalledWithInvalidCoordinates() {
        val gridX = 20
        val gridZ = 30
        val result = target.getByCubeCoordinate(fromCoordinates(gridX, gridZ))
        assertFalse(result.isPresent)
    }

    @Test
    fun shouldReturnHexagonWhenCalledWithProperCoordinates() {
        val x = 310.0
        val y = 255.0
        val hex = target.getByPixelCoordinate(x, y).get()
        assertEquals(hex.gridX, 3)
        assertEquals(hex.gridZ, 5)
    }

    @Test
    fun shouldReturnHexagonWhenCalledWithProperCoordinates2() {
        val x = 300.0
        val y = 275.0
        val hex = target.getByPixelCoordinate(x, y).get()
        assertEquals(hex.gridX, 3)
        assertEquals(hex.gridZ, 5)
    }

    @Test
    fun shouldReturnHexagonWhenCalledWithProperCoordinates3() {
        val x = 325.0
        val y = 275.0
        val hex = target.getByPixelCoordinate(x, y).get()
        assertEquals(hex.gridX, 3)
        assertEquals(hex.gridZ, 5)
    }

    @Test
    fun shouldReturnProperNeighborsOfHexagonWhenHexIsInMiddle() {
        val hex = target.getByCubeCoordinate(fromCoordinates(3, 7)).get()
        val expected = HashSet<Hexagon<DefaultSatelliteData>>()
        expected.add(target.getByCubeCoordinate(fromCoordinates(3, 6)).get())
        expected.add(target.getByCubeCoordinate(fromCoordinates(4, 6)).get())
        expected.add(target.getByCubeCoordinate(fromCoordinates(4, 7)).get())
        expected.add(target.getByCubeCoordinate(fromCoordinates(3, 8)).get())
        expected.add(target.getByCubeCoordinate(fromCoordinates(2, 8)).get())
        expected.add(target.getByCubeCoordinate(fromCoordinates(2, 7)).get())
        val actual = target.getNeighborsOf(hex)
        assertEquals(expected, actual)
    }

    @Test
    abstract fun shouldReturnProperNeighborsOfHexagonWhenHexIsOnTheEdge()

    @Test
    abstract fun shouldProperlyReturnGridLayoutWhenGetGridLayoutIsCalled()

    @Test
    fun shouldProperlyReturnSharedHexagonDataWhenGetSharedHexagonDataIsCalled() {
        assertEquals(builder.gridData.hexagonHeight, target.gridData.hexagonHeight)
        assertEquals(builder.gridData.hexagonWidth, target.gridData.hexagonWidth)
        assertEquals(builder.gridData.radius, target.gridData.radius)
        assertEquals(builder.gridData.orientation, target.gridData.orientation)
    }

    @Test
    fun shouldProperlyReturnGridWidthWhenGetGridWidthIsCalled() {
        assertEquals(GRID_WIDTH, target.gridData.gridWidth)
    }

    @Test
    fun shouldProperlyReturnGridHeightWhenGetGridHeightIsCalled() {
        assertEquals(GRID_HEIGHT, target.gridData.gridHeight)
    }

    companion object {

        internal const val RADIUS = 30
        internal const val GRID_WIDTH = 10
        internal const val GRID_HEIGHT = 10
        private const val GRID_X_FROM = 2
        private const val GRID_X_TO = 4
        private const val GRID_Z_FROM = 3
        private const val GRID_Z_TO = 5
        internal val ORIENTATION = HexagonOrientation.POINTY_TOP
    }
}
