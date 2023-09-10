package org.hexworks.mixite2.core.internal.impl

import org.hexworks.mixite2.core.api.CubeCoordinate.Companion.fromCoordinates
import org.hexworks.mixite2.core.api.Hexagon
import org.hexworks.mixite2.core.api.HexagonOrientation.FLAT_TOP
import org.hexworks.mixite2.core.api.HexagonOrientation.POINTY_TOP
import org.hexworks.mixite2.core.api.HexagonalGridLayout.RECTANGULAR
import org.hexworks.mixite2.core.api.Point.Companion.fromPosition
import org.hexworks.mixite2.core.api.defaults.DefaultHexagonDataStorage
import org.hexworks.mixite2.core.api.defaults.DefaultSatelliteData
import org.hexworks.mixite2.core.internal.GridData
import kotlin.math.round
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class HexagonImplTest {

    private lateinit var target: Hexagon<DefaultSatelliteData>

    @BeforeTest
    fun setUp() {
        target = HexagonImpl(TEST_POINTY_DATA, TEST_COORDINATE, TEST_SATELLITE_DATA_MAP)
    }

    @Test
    fun shouldHaveProperPointsWhenPointy() {
        val points = ArrayList(target.points)
        for (i in 0..5) {
            assertEquals(EXPECTED_POINTY_POINTS[i].coordinateX.toInt(), round(points[i].coordinateX).toInt())
            assertEquals(EXPECTED_POINTY_POINTS[i].coordinateY.toInt(), round(points[i].coordinateY).toInt())
        }
    }

    @Test
    fun shouldHaveProperPointsWhenFlat() {
        target = HexagonImpl(TEST_FLAT_DATA, TEST_COORDINATE, TEST_SATELLITE_DATA_MAP)
        val points = ArrayList(target.points)
        for (i in 0..5) {
            assertEquals(EXPECTED_FLAT_POINTS[i].coordinateX.toInt(), round(points[i].coordinateX).toInt())
            assertEquals(EXPECTED_FLAT_POINTS[i].coordinateY.toInt(), round(points[i].coordinateY).toInt())
        }
    }

    @Test
    fun shouldReturnProperSatelliteDataWhenSatelliteDataIsSet() {
        target.setSatelliteData(TEST_SATELLITE_DATA)
        assertEquals(TEST_SATELLITE_DATA, target.satelliteData.get())
    }

    @Test
    fun shouldReturnProperSatelliteDataWhenSatelliteDataIsCleared() {
        target.setSatelliteData(TEST_SATELLITE_DATA)
        assertEquals(TEST_SATELLITE_DATA, target.satelliteData.get())
        target.clearSatelliteData()
        assertFalse(target.satelliteData.isPresent)
    }

    @Test
    fun shouldReturnProperXCoordinateWhenGetGridXIsCalled() {
        assertEquals(TEST_GRID_X, target.gridX)
    }

    @Test
    fun shouldReturnProperXCoordinateWhenGetGridYIsCalled() {
        assertEquals(TEST_GRID_Y, target.gridY)
    }

    @Test
    fun shouldReturnProperXCoordinateWhenGetGridZIsCalled() {
        assertEquals(TEST_GRID_Z, target.gridZ)
    }

    @Test
    fun shouldReturnProperCenterXCoordinateWhenGetCenterXIsCalledWithPointyHexagons() {
        assertEquals(EXPECTED_POINTY_CENTER_X, round(target.center.coordinateX).toInt())
    }

    @Test
    fun shouldReturnProperCenterXCoordinateWhenGetCenterXIsCalledWithFlatHexagons() {
        target = HexagonImpl(TEST_FLAT_DATA, TEST_COORDINATE, TEST_SATELLITE_DATA_MAP)
        assertEquals(EXPECTED_FLAT_CENTER_X, round(target.center.coordinateX).toInt())
    }

    @Test
    fun shouldReturnProperCenterYCoordinateWhenGetCenterYIsCalledWithPointyHexagons() {
        assertEquals(EXPECTED_POINTY_CENTER_Y, round(target.center.coordinateY).toInt())
    }

    @Test
    fun shouldReturnProperCenterYCoordinateWhenGetCenterYIsCalledWithFlatHexagons() {
        target = HexagonImpl(TEST_FLAT_DATA, TEST_COORDINATE, TEST_SATELLITE_DATA_MAP)
        assertEquals(EXPECTED_FLAT_CENTER_Y, round(target.center.coordinateY).toInt())
    }

    @Test
    fun shouldBeEqualToItself() {
        assertEquals(target, target)
    }

    @Test
    fun shouldProperlyGetIdWhenGetIdIsCalled() {
        assertEquals(TEST_COORDINATE.toAxialKey(), target.id)
    }

    companion object {
        private const val TEST_RADIUS = 10.0
        private const val TEST_GRID_X = 2
        private const val TEST_GRID_Z = 3
        private const val TEST_GRID_Y = -5
        private const val EXPECTED_POINTY_CENTER_X = 69
        private const val EXPECTED_FLAT_CENTER_X = 40
        private const val EXPECTED_POINTY_CENTER_Y = 55
        private const val EXPECTED_FLAT_CENTER_Y = 78
        private val TEST_POINTY_DATA = GridData(POINTY_TOP, RECTANGULAR.gridLayoutStrategy, TEST_RADIUS, 1, 1)
        private val TEST_FLAT_DATA = GridData(FLAT_TOP, RECTANGULAR.gridLayoutStrategy, TEST_RADIUS, 1, 1)
        private val TEST_COORDINATE = fromCoordinates(TEST_GRID_X, TEST_GRID_Z)
        private val TEST_SATELLITE_DATA = DefaultSatelliteData()
        private val TEST_SATELLITE_DATA_MAP = DefaultHexagonDataStorage<DefaultSatelliteData>()
        private val EXPECTED_FLAT_POINTS = arrayOf(fromPosition(50.0, 78.0), fromPosition(45.0, 87.0), fromPosition(35.0, 87.0), fromPosition(30.0, 78.0), fromPosition(35.0, 69.0), fromPosition(45.0, 69.0))
        private val EXPECTED_POINTY_POINTS = arrayOf(fromPosition(78.0, 60.0), fromPosition(69.0, 65.0), fromPosition(61.0, 60.0), fromPosition(61.0, 50.0), fromPosition(69.0, 45.0), fromPosition(78.0, 50.0))

        init {
            TEST_SATELLITE_DATA_MAP.addCoordinate(TEST_COORDINATE, TEST_SATELLITE_DATA)
        }
    }
}
