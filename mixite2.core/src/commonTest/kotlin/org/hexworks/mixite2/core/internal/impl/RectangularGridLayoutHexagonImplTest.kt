package org.hexworks.mixite2.core.internal.impl

import org.hexworks.mixite2.core.api.CoordinateConverter
import org.hexworks.mixite2.core.api.CubeCoordinate
import org.hexworks.mixite2.core.api.Hexagon
import org.hexworks.mixite2.core.api.HexagonalGridBuilder
import org.hexworks.mixite2.core.api.defaults.DefaultSatelliteData
import org.hexworks.mixite2.core.internal.impl.layoutstrategy.RectangularGridLayoutStrategy
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RectangularGridLayoutHexagonImplTest : HexagonalGridImplTest() {

    override fun getBuilder(): HexagonalGridBuilder<DefaultSatelliteData> {
        return HexagonalGridBuilder<DefaultSatelliteData>()
                .setGridHeight(GRID_HEIGHT)
                .setGridWidth(GRID_WIDTH)
                .setRadius(RADIUS.toDouble())
                .setOrientation(ORIENTATION)
    }

    override fun shouldReturnProperHexagonsWhenEachHexagonIsTestedInAGivenGrid() {
        val hexagons = target.hexagons
        val expectedCoordinates = HashSet<String>()
        for (x in 0 until GRID_WIDTH) {
            for (y in 0 until GRID_HEIGHT) {
                val gridX = org.hexworks.mixite2.core.api.CoordinateConverter.convertOffsetCoordinatesToCubeX(x, y, ORIENTATION)
                val gridZ = org.hexworks.mixite2.core.api.CoordinateConverter.convertOffsetCoordinatesToCubeZ(x, y, ORIENTATION)
                expectedCoordinates.add("$gridX,$gridZ")
            }
        }
        var count = 0
        for (hexagon in hexagons) {
            expectedCoordinates.remove("${hexagon.gridX},${hexagon.gridZ}")
            count++
        }
        assertEquals(100, count)
        assertTrue(expectedCoordinates.isEmpty())
    }

    override fun shouldReturnProperNeighborsOfHexagonWhenHexIsOnTheEdge() {
        val hex = target.getByCubeCoordinate(org.hexworks.mixite2.core.api.CubeCoordinate.fromCoordinates(5, 9)).get()
        val expected = HashSet<Hexagon<DefaultSatelliteData>>()
        expected.add(target.getByCubeCoordinate(org.hexworks.mixite2.core.api.CubeCoordinate.fromCoordinates(5, 8)).get())
        expected.add(target.getByCubeCoordinate(org.hexworks.mixite2.core.api.CubeCoordinate.fromCoordinates(4, 9)).get())
        val actual = target.getNeighborsOf(hex)
        assertEquals(expected, actual)
    }

    override fun shouldProperlyReturnGridLayoutWhenGetGridLayoutIsCalled() {
        assertTrue(target.gridData.gridLayout is RectangularGridLayoutStrategy)
    }
}